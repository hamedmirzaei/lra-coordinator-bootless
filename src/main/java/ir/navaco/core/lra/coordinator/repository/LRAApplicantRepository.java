package ir.navaco.core.lra.coordinator.repository;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LRAApplicantRepository extends JpaRepository<LRAApplicantEntity, Long> {
}
