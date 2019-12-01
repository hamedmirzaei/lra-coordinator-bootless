package ir.navaco.core.lra.coordinator.repository;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LRAInstanceExecutionRepository extends JpaRepository<LRAInstanceExecutionEntity, Long> {
}
