package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceExecutionEntity;
import ir.navaco.core.lra.coordinator.repository.LRAInstanceExecutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(LRAInstanceExecutionServiceImpl.BEAN_NAME)
@Transactional
public class LRAInstanceExecutionServiceImpl implements LRAInstanceExecutionService {

    public static final String BEAN_NAME = "lraInstanceExecutionServiceImpl";

    private LRAInstanceExecutionRepository lraInstanceExecutionRepository;

    @Override
    public LRAInstanceExecutionEntity saveLRAInstanceExecution(LRAInstanceExecutionEntity lraInstanceExecutionEntity) {
        return lraInstanceExecutionRepository.save(lraInstanceExecutionEntity);
    }

    @Autowired
    public void setLraInstanceExecutionRepository(LRAInstanceExecutionRepository lraInstanceExecutionRepository) {
        this.lraInstanceExecutionRepository = lraInstanceExecutionRepository;
    }
}
