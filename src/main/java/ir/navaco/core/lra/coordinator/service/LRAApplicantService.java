package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantRegisterRequestTypeVo;

public interface LRAApplicantService {

    LRAApplicantEntity registerLRAApplicant(LRAApplicantRegisterRequestTypeVo lraApplicantRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, LRARequestException.InternalException;

    LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws LRARequestException.InternalException;

    LRAApplicantEntity saveLRAApplicant(LRAApplicantEntity lraApplicantEntity) throws LRARequestException.InternalException;
}
