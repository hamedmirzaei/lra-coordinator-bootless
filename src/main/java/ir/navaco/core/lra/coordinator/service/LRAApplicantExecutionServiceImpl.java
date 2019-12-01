package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantExecutionEntity;
import ir.navaco.core.lra.coordinator.repository.LRAApplicantExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(LRAApplicantExecutionServiceImpl.BEAN_NAME)
@Transactional
public class LRAApplicantExecutionServiceImpl implements LRAApplicantExecutionService {

    public static final String BEAN_NAME = "lraApplicantExecutionServiceImpl";

    private LRAApplicantExecutionRepository lraApplicantExecutionRepository;

    @Override
    public LRAApplicantExecutionEntity saveLRAApplicantExecution(LRAApplicantExecutionEntity lraApplicantExecutionEntity) {
        return lraApplicantExecutionRepository.save(lraApplicantExecutionEntity);
    }

    @Autowired
    public void setLraApplicantExecutionRepository(LRAApplicantExecutionRepository lraApplicantExecutionRepository) {
        this.lraApplicantExecutionRepository = lraApplicantExecutionRepository;
    }
}
