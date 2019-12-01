package ir.navaco.core.lra.coordinator.service.utils;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.service.LRAApplicantExecutionService;
import ir.navaco.core.lra.coordinator.service.LRAApplicantService;
import ir.navaco.core.lra.coordinator.service.LRAInstanceExecutionService;
import ir.navaco.core.lra.coordinator.service.LRAInstanceService;
import ir.navaco.core.lra.coordinator.utils.Constants;

import java.util.List;
import java.util.concurrent.*;

public class CancelHandlerThread implements Runnable {

    private LRAInstanceService lraInstanceService;
    private LRAInstanceExecutionService lraInstanceExecutionService;
    private LRAApplicantService lraApplicantService;
    private LRAApplicantExecutionService lraApplicantExecutionService;

    ExecutorService executor;

    public CancelHandlerThread(LRAInstanceService lraInstanceService, LRAInstanceExecutionService lraInstanceExecutionService, LRAApplicantService lraApplicantService, LRAApplicantExecutionService lraApplicantExecutionService) {
        this.lraInstanceService = lraInstanceService;
        this.lraInstanceExecutionService = lraInstanceExecutionService;
        this.lraApplicantService = lraApplicantService;
        this.lraApplicantExecutionService = lraApplicantExecutionService;
    }

    /**
     * the background thread for extracting CANCEL_REQUESTs and
     * processing them for compensation operation
     */
    public void run() {
        int waitTime = Constants.lraProperties.getCancelHandlerWaitTimeRate(); // wait for 4 secs
        while (true) {
            executor = new ThreadPoolExecutor(Constants.lraProperties.getCorePoolSize(),
                    Constants.lraProperties.getMaximumPoolSize(), 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            // Get CANCEL_REQUEST instances from db and process them
            List<LRAInstanceEntity> lraInstanceEntities = lraInstanceService.findAllByLRAInstanceStatus(LRAInstanceStatus.CANCEL_REQUEST);
            if (lraInstanceEntities == null || lraInstanceEntities.size() == 0) {
                try {
                    Thread.sleep(waitTime * 1000);
                    waitTime += Constants.lraProperties.getCancelHandlerWaitTimeRate();// wait more next time
                    if (waitTime == Constants.lraProperties.getCancelHandlerWaitTimeMax())
                        waitTime = Constants.lraProperties.getCancelHandlerWaitTimeRate();
                } catch (InterruptedException e) {
                }
            } else {
                waitTime = Constants.lraProperties.getCancelHandlerWaitTimeRate();
                process(lraInstanceEntities);
            }
        }
    }

    /**
     * this method process all the current CANCEL_REQUEST LRAInstances
     *
     * @param lraInstanceEntities list of LRAInstances
     */
    private void process(List<LRAInstanceEntity> lraInstanceEntities) {
        for (LRAInstanceEntity lraInstanceEntity : lraInstanceEntities) {
            Future<Boolean> resultFuture = executor.submit(
                    new LRAInstanceHandlerThread(lraInstanceService, lraInstanceExecutionService, lraApplicantService, lraApplicantExecutionService, lraInstanceEntity));
            try {
                Boolean result = resultFuture.get();
                if (result) {
                    lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CANCELED);
                } else {
                    lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.FAILED);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.FAILED);
            }
            try {
                lraInstanceService.updateLRAInstance(lraInstanceEntity);
            } catch (SystemException.InternalException e) {
                e.printStackTrace();
            }
        }

        try {
            executor.shutdown();
            // wait for existing threads to be finished
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Exception raised during waiting for threads to be finished");
        }
    }

}
