package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.repository.LRAInstanceRepository;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service(LRAInstanceServiceImpl.BEAN_NAME)
@Transactional
public class LRAInstanceServiceImpl implements LRAInstanceService {

    public static final String BEAN_NAME = "lraInstanceServiceImpl";

    private LRAInstanceRepository lraInstanceRepository;

    @Override
    public LRAInstanceEntity saveLRAInstance(Map<String, String> input)
            throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap {
        MapUtils.validate(input, 2, "timeout", "retry-limit");
        LRAInstanceEntity lraInstanceEntity = new LRAInstanceEntity();
        lraInstanceEntity.setUuid("this-is-sample-uuid");//TODO UUID.randomUUID().toString()
        lraInstanceEntity.setRetryLimit(Integer.valueOf(input.get("retry-limit")));
        lraInstanceEntity.setTimeout(Integer.valueOf(input.get("timeout")));
        lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CREATED);
        return lraInstanceRepository.save(lraInstanceEntity);
    }

    @Override
    public void cancelLRAInstance(Map<String, String> input)
            throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap, LRAException.InstanceNotFoundException {
        MapUtils.validate(input, 1, "uuid");
        String uuid = input.get("uuid");
        LRAInstanceEntity lraInstanceEntity = findByUuid(uuid);
        if (lraInstanceEntity == null)
            throw new LRAException.InstanceNotFoundException(uuid);
        //TODO what should be the status? CANCELING or CANCEL_REQUEST
        lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CANCEL_REQUEST);
        lraInstanceRepository.save(lraInstanceEntity);
        //TODO cancel LRA (call applicants) based on timeout, retry-limit and
        //     eurekaDiscovery => where is the right place to do it
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
