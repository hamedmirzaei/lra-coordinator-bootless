package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantVo;

public interface LRAApplicantService {

    LRAApplicantEntity registerLRAApplicant(LRAApplicantVo lraApplicantVo)
            throws LRAException.InstanceNotFoundException;

    LRAApplicantEntity updateLRAApplicant(LRAApplicantEntity lraApplicantEntity);
}
