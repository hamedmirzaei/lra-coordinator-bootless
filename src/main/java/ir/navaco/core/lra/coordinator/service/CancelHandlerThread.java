package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;

import java.util.List;
import java.util.concurrent.*;

public class CancelHandlerThread implements Runnable {

    private LRAInstanceService lraInstanceService;
    private LRAApplicantService lraApplicantService;

    ExecutorService executor;

    public CancelHandlerThread(LRAInstanceService lraInstanceService, LRAApplicantService lraApplicantService) {
        this.lraInstanceService = lraInstanceService;
        this.lraApplicantService = lraApplicantService;
    }

    public void run() {
        int waitTime = 10 * 1000; // 10 secs
        while (true) {
            executor = new ThreadPoolExecutor(100,
                    100, 0L,
                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
            // Get CANCEL_REQUEST instances from db and process them
            List<LRAInstanceEntity> lraInstanceEntities = lraInstanceService.findAllByLRAInstanceStatus(LRAInstanceStatus.CANCEL_REQUEST);
            if (lraInstanceEntities == null || lraInstanceEntities.size() == 0) {
                try {
                    Thread.sleep(waitTime);
                    waitTime += 10 * 1000;// add another 10 secs
                } catch (InterruptedException e) {
                }
            } else {
                waitTime = 10 * 1000;// reset to 10 secs
                process(lraInstanceEntities);
            }
        }
        // Do any thread cooldown procedures here, like stop listening to the Queue.
    }

    private void process(List<LRAInstanceEntity> lraInstanceEntities) {
        for (LRAInstanceEntity lraInstanceEntity : lraInstanceEntities) {
            lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.FAILED);
            Future<Boolean> resultFuture = executor.submit(
                    new LRAInstanceHandlerThread(lraInstanceService, lraApplicantService, lraInstanceEntity));
            try {
                Boolean result = resultFuture.get();
                if (result) {
                    lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CANCELED);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                lraInstanceService.updateLRAInstance(lraInstanceEntity);
            } catch (LRARequestException.InternalException e) {
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
