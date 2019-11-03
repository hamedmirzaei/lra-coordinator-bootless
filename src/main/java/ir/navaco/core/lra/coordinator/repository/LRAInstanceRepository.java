package ir.navaco.core.lra.coordinator.repository;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LRAInstanceRepository extends JpaRepository<LRAInstanceEntity, Long> {
    LRAInstanceEntity findByUuid(String uuid);
}
