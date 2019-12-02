package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantType;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.repository.LRAApplicantRepository;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantDirectRegisterRequestTypeVo;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantEurekaRegisterRequestTypeVo;
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
    public LRAApplicantEntity registerEurekaLRAApplicant(LRAApplicantEurekaRegisterRequestTypeVo lraApplicantEurekaRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        LRAInstanceEntity instance = lraInstanceService.findByUuid(lraApplicantEurekaRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (instance == null)
            throw new LRAException.InstanceNotFoundException(lraApplicantEurekaRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (lraInstanceService.isItProcessed(instance))
            throw new LRAException.InstanceAlreadyProcessedException(lraApplicantEurekaRegisterRequestTypeVo.getLraInstanceEntityUUID());
        LRAApplicantEntity lraApplicantEntity = new LRAApplicantEntity();
        lraApplicantEntity.setLraInstanceEntity(instance);
        lraApplicantEntity.setAppName(lraApplicantEurekaRegisterRequestTypeVo.getAppName());
        lraApplicantEntity.setServiceName(lraApplicantEurekaRegisterRequestTypeVo.getServiceName());
        lraApplicantEntity.setHttpMethod(lraApplicantEurekaRegisterRequestTypeVo.getHttpMethod());
        lraApplicantEntity.setPathVariables(lraApplicantEurekaRegisterRequestTypeVo.getPathVariables());
        if (lraApplicantEurekaRegisterRequestTypeVo.getRequestParameters() != null)
            lraApplicantEntity.setRequestParameters(MapUtils.mapToString(lraApplicantEurekaRegisterRequestTypeVo.getRequestParameters()));
        lraApplicantEntity.setRequestBodyInJSON(lraApplicantEurekaRegisterRequestTypeVo.getRequestBodyInJSON());
        lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.REGISTERED);
        lraApplicantEntity.setConnectTimeout(lraApplicantEurekaRegisterRequestTypeVo.getConnectTimeout());
        lraApplicantEntity.setReadTimeout(lraApplicantEurekaRegisterRequestTypeVo.getReadTimeout());
        lraApplicantEntity.setLraApplicantType(LRAApplicantType.EUREKA);
        return saveLRAApplicant(lraApplicantEntity);
    }

    @Override
    public LRAApplicantEntity registerDirectLRAApplicant(LRAApplicantDirectRegisterRequestTypeVo lraApplicantDirectRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        LRAInstanceEntity instance = lraInstanceService.findByUuid(lraApplicantDirectRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (instance == null)
            throw new LRAException.InstanceNotFoundException(lraApplicantDirectRegisterRequestTypeVo.getLraInstanceEntityUUID());
        if (lraInstanceService.isItProcessed(instance))
            throw new LRAException.InstanceAlreadyProcessedException(lraApplicantDirectRegisterRequestTypeVo.getLraInstanceEntityUUID());
        LRAApplicantEntity lraApplicantEntity = new LRAApplicantEntity();
        lraApplicantEntity.setLraInstanceEntity(instance);
        lraApplicantEntity.setBaseUrl(lraApplicantDirectRegisterRequestTypeVo.getBaseUrl());
        lraApplicantEntity.setHttpMethod(lraApplicantDirectRegisterRequestTypeVo.getHttpMethod());
        if (lraApplicantDirectRegisterRequestTypeVo.getRequestParameters() != null)
            lraApplicantEntity.setRequestParameters(MapUtils.mapToString(lraApplicantDirectRegisterRequestTypeVo.getRequestParameters()));
        lraApplicantEntity.setRequestBodyInJSON(lraApplicantDirectRegisterRequestTypeVo.getRequestBodyInJSON());
        lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.REGISTERED);
        lraApplicantEntity.setConnectTimeout(lraApplicantDirectRegisterRequestTypeVo.getConnectTimeout());
        lraApplicantEntity.setReadTimeout(lraApplicantDirectRegisterRequestTypeVo.getReadTimeout());
        lraApplicantEntity.setLraApplicantType(LRAApplicantType.DIRECT);
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
