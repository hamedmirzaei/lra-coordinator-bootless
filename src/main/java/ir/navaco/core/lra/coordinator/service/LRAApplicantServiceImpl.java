package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.repository.LRAApplicantRepository;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(LRAApplicantServiceImpl.BEAN_NAME)
@Transactional
public class LRAApplicantServiceImpl implements LRAApplicantService {

    public static final String BEAN_NAME = "lraApplicantServiceImpl";

    private LRAApplicantRepository lraApplicantRepository;
    private LRAInstanceService lraInstanceService;

    @Override
    public LRAApplicantEntity registerLRAApplicant(LRAApplicantVo lraApplicantVo)
            throws LRAException.InstanceNotFoundException {
        LRAInstanceEntity instance = lraInstanceService.findByUuid(lraApplicantVo.getLraInstanceEntityUUID());
        if (instance == null)
            throw new LRAException.InstanceNotFoundException(lraApplicantVo.getLraInstanceEntityUUID());
        LRAApplicantEntity lraApplicantEntity = new LRAApplicantEntity();
        lraApplicantEntity.setLraInstanceEntity(instance);
        lraApplicantEntity.setAppName(lraApplicantVo.getAppName());
        lraApplicantEntity.setServiceName(lraApplicantVo.getServiceName());
        lraApplicantEntity.setHttpMethod(lraApplicantVo.getHttpMethod());
        lraApplicantEntity.setPathVariables(lraApplicantVo.getPathVariables());
        if (lraApplicantVo.getRequestParameters() != null)
            lraApplicantEntity.setRequestParameters(MapUtils.mapToString(lraApplicantVo.getRequestParameters()));
        lraApplicantEntity.setRequestBodyInJSON(lraApplicantVo.getRequestBodyInJSON());
        lraApplicantEntity.setLraApplicantStatus(lraApplicantVo.getLraApplicantStatus());
        return lraApplicantRepository.save(lraApplicantEntity);
    }

    @Override
    public LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity) {
        return lraApplicantRepository.save(lraApplicantEntity);
    }

    @Autowired
    public void setLraApplicantRepository(LRAApplicantRepository lraApplicantRepository) {
        this.lraApplicantRepository = lraApplicantRepository;
    }

    @Autowired
    @Qualifier(LRAInstanceServiceImpl.BEAN_NAME)
    public void setLraInstanceService(LRAInstanceService lraInstanceService) {
        this.lraInstanceService = lraInstanceService;
    }
}
