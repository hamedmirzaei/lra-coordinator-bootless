package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCancelRequestTypeVo;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCreateRequestTypeVo;

public interface LRAInstanceService {

    LRAInstanceEntity saveLRAInstance(LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo)
            throws LRARequestException.InternalException;

    void cancelLRAInstance(LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo)
            throws LRAException.InstanceNotFoundException, LRARequestException.InternalException;

    LRAInstanceEntity findByUuid(String uuid);
}
