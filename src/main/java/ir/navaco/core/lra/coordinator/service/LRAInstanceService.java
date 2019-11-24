package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCancelRequestTypeVo;
import ir.navaco.core.lra.coordinator.vo.LRAInstanceCreateRequestTypeVo;

import java.util.List;

public interface LRAInstanceService {

    LRAInstanceEntity createLRAInstance(LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo)
            throws LRARequestException.InternalException;

    void cancelLRAInstance(LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo)
            throws LRAException.InstanceNotFoundException, LRARequestException.InternalException;

    LRAInstanceEntity findByUuid(String uuid);

    List<LRAInstanceEntity> findAllByLRAInstanceStatus(LRAInstanceStatus lraInstanceStatus);

    LRAInstanceEntity updateLRAInstance(LRAInstanceEntity lraInstanceEntity) throws LRARequestException.InternalException;

    LRAInstanceEntity saveLRAInstance(LRAInstanceEntity lraInstanceEntity) throws LRARequestException.InternalException;

}
