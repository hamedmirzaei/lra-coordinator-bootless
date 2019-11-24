package ir.navaco.core.lra.coordinator.repository;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LRAInstanceRepository extends JpaRepository<LRAInstanceEntity, Long> {
    LRAInstanceEntity findByUuid(String uuid);

    List<LRAInstanceEntity> findAllByLraInstanceStatus(LRAInstanceStatus lraInstanceStatus);
}
