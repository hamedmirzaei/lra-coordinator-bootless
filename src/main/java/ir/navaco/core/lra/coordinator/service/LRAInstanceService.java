package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;

import java.util.Map;

public interface LRAInstanceService {

    LRAInstanceEntity saveLRAInstance(Map<String, String> input)
            throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap;

    void cancelLRAInstance(Map<String, String> input)
            throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap, LRAException.InstanceNotFoundException;

    LRAInstanceEntity findByUuid(String uuid);
}
