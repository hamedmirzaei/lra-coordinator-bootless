package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantRegisterRequestTypeVo;

public interface LRAApplicantService {

    LRAApplicantEntity registerLRAApplicant(LRAApplicantRegisterRequestTypeVo lraApplicantRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException;

    LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException;

    LRAApplicantEntity saveLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws SystemException.InternalException;
}
