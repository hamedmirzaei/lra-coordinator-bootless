package ir.navaco.core.lra.coordinator.repository;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LRAApplicantExecutionRepository extends JpaRepository<LRAApplicantExecutionEntity, Long> {
}
