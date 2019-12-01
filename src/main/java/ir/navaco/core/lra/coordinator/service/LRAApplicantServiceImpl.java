package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.repository.LRAApplicantRepository;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantRegisterRequestTypeVo;
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
    public LRAApplicantEntity registerLRAApplicant(LRAApplicantRegisterRequestTypeVo lraApplicantRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        LRAInstanceEntity instance = lraInstanceService.findByUuid(lraApplicantRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (instance == null)
            throw new LRAException.InstanceNotFoundException(lraApplicantRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (lraInstanceService.isItProcessed(instance))
            throw new LRAException.InstanceAlreadyProcessedException(lraApplicantRegisterRequestTypeVo.getLraInstanceEntityUUID());
        LRAApplicantEntity lraApplicantEntity = new LRAApplicantEntity();
        lraApplicantEntity.setLraInstanceEntity(instance);
        lraApplicantEntity.setAppName(lraApplicantRegisterRequestTypeVo.getAppName());
        lraApplicantEntity.setServiceName(lraApplicantRegisterRequestTypeVo.getServiceName());
        lraApplicantEntity.setHttpMethod(lraApplicantRegisterRequestTypeVo.getHttpMethod());
        lraApplicantEntity.setPathVariables(lraApplicantRegisterRequestTypeVo.getPathVariables());
        if (lraApplicantRegisterRequestTypeVo.getRequestParameters() != null)
            lraApplicantEntity.setRequestParameters(MapUtils.mapToString(lraApplicantRegisterRequestTypeVo.getRequestParameters()));
        lraApplicantEntity.setRequestBodyInJSON(lraApplicantRegisterRequestTypeVo.getRequestBodyInJSON());
        lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.REGISTERED);
        lraApplicantEntity.setConnectTimeout(lraApplicantRegisterRequestTypeVo.getConnectTimeout());
        lraApplicantEntity.setReadTimeout(lraApplicantRegisterRequestTypeVo.getReadTimeout());
        return saveLRAApplicant(lraApplicantEntity);
    }

    @Override
    public LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException {
        try {
            return lraApplicantRepository.save(lraApplicantEntity);
        } catch (Exception e) {
            throw new SystemException.InternalException("database exception occurred during updating LRA Applicant: " + lraApplicantEntity);
        }
    }

    @Override
    public LRAApplicantEntity saveLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException {
        try {
            return lraApplicantRepository.save(lraApplicantEntity);
        } catch (Exception e) {
            throw new SystemException.InternalException("database exception occurred during saving LRA Applicant: " + lraApplicantEntity);
        }
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
