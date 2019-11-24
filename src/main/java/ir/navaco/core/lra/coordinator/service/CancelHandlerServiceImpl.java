package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.service.utils.CancelHandlerThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service(CancelHandlerServiceImpl.BEAN_NAME)
public class CancelHandlerServiceImpl implements CancelHandlerService {

    public static final String BEAN_NAME = "cancelHandlerServiceImpl";

    private LRAInstanceService lraInstanceService;
    private LRAApplicantService lraApplicantService;

    @PostConstruct
    public void makeBackgroundThread() {
        Thread thread = new Thread(new CancelHandlerThread(lraInstanceService, lraApplicantService));
        thread.start();
    }

    @Autowired
    @Qualifier(LRAInstanceServiceImpl.BEAN_NAME)
    public void setLraInstanceService(LRAInstanceService lraInstanceService) {
        this.lraInstanceService = lraInstanceService;
    }

    @Autowired
    @Qualifier(LRAApplicantServiceImpl.BEAN_NAME)
    public void setLraApplicantService(LRAApplicantService lraApplicantService) {
        this.lraApplicantService = lraApplicantService;
    }

}
