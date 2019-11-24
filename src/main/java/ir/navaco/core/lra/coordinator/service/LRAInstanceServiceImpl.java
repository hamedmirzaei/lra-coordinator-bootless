package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.repository.LRAInstanceRepository;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCancelRequestTypeVo;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCreateRequestTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(LRAInstanceServiceImpl.BEAN_NAME)
@Transactional
public class LRAInstanceServiceImpl implements LRAInstanceService {

    public static final String BEAN_NAME = "lraInstanceServiceImpl";

    private LRAInstanceRepository lraInstanceRepository;

    @Override
    public LRAInstanceEntity saveLRAInstance(LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo)
            throws LRARequestException.InternalException {
        LRAInstanceEntity lraInstanceEntity = new LRAInstanceEntity();
        lraInstanceEntity.setUuid("this-is-sample-uuid");//TODO UUID.randomUUID().toString()
        lraInstanceEntity.setRetryLimit(lraInstanceCreateRequestTypeVo.getRetryLimit());
        lraInstanceEntity.setTimeout(lraInstanceCreateRequestTypeVo.getTimeout());
        lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CREATED);
        try {
            lraInstanceEntity = lraInstanceRepository.save(lraInstanceEntity);
        } catch (Exception e) {
            throw new LRARequestException.InternalException("database exception occurred during saving LRA Instance: " + lraInstanceEntity);
        }
        return lraInstanceEntity;
    }

    @Override
    public void cancelLRAInstance(LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo)
            throws LRAException.InstanceNotFoundException, LRARequestException.InternalException {
        LRAInstanceEntity lraInstanceEntity = findByUuid(lraInstanceCancelRequestTypeVo.getUuid());
        if (lraInstanceEntity == null)
            throw new LRAException.InstanceNotFoundException(lraInstanceCancelRequestTypeVo.getUuid());
        //what should be the status? CANCELING or CANCEL_REQUEST: CANCEL_REQUEST
        lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CANCEL_REQUEST);
        try {
            lraInstanceRepository.save(lraInstanceEntity);
        } catch (Exception e) {
            throw new LRARequestException.InternalException("database exception occurred during canceling LRA Instance: " + lraInstanceEntity);
        }
        //TODO cancel LRA (call applicants) based on timeout, retry-limit and
        //     eurekaDiscovery => where is the right place to do it?
        //     make a thread to process a single lraApplicant, then do it for all concurrently

        lraInstanceEntity.getLraApplicantEntities();
    }

    @Override
    public LRAInstanceEntity findByUuid(String uuid) {
        return lraInstanceRepository.findByUuid(uuid);
    }

    @Autowired
    public void setLraInstanceRepository(LRAInstanceRepository lraInstanceRepository) {
        this.lraInstanceRepository = lraInstanceRepository;
    }
}
