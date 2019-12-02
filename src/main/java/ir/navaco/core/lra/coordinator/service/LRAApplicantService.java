package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantDirectRegisterRequestTypeVo;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantEurekaRegisterRequestTypeVo;

public interface LRAApplicantService {

    LRAApplicantEntity registerDirectLRAApplicant(LRAApplicantDirectRegisterRequestTypeVo lraApplicantDirectRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException;

    LRAApplicantEntity registerEurekaLRAApplicant(LRAApplicantEurekaRegisterRequestTypeVo lraApplicantEurekaRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException;

    LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException;

    LRAApplicantEntity saveLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException;
}
