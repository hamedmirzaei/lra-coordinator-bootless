package ir.navaco.core.lra.coordinator.service;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.utils.HttpUtils;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Map;

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
