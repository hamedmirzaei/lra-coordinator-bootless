package ir.navaco.core.lra.coordinator.api;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.service.*;
import ir.navaco.core.lra.coordinator.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class LRACoordinatorApi {

    private LRAInstanceService lraInstanceService;
    private LRAApplicantService lraApplicantService;
    private CancelHandlerService cancelHandlerService;

    /**
     * health check
     *
     * @return
     */
    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public String healthCheck() {
        return "Server is UP";
    }

    /**
     * this will generates a LRA instance, persist it in DB and returns its UUID
     *
     * @param lraInstanceCreateRequestTypeVo request type
     * @return UUID of LRA instance or failure message based on HttpStatus
     */
    @PostMapping(value = "/instance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAInstanceCreateResponseTypeVo> createLRA(@RequestBody LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo) throws LRARequestException.InternalException {
        LRAInstanceEntity lraInstanceEntity = lraInstanceService.createLRAInstance(lraInstanceCreateRequestTypeVo);
        return ResponseEntity.ok(new LRAInstanceCreateResponseTypeVo(lraInstanceEntity.getUuid()));
    }

    /**
     * this will cancel a LRA instance
     *
     * @param lraInstanceCancelRequestTypeVo request type
     * @return message for failed and successful execution
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/instance/cancel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAInstanceCancelResponseTypeVo> cancelLRA(@RequestBody LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo) throws LRAException.InstanceNotFoundException, LRARequestException.InternalException {
        lraInstanceService.cancelLRAInstance(lraInstanceCancelRequestTypeVo);
        return ResponseEntity.ok(new LRAInstanceCancelResponseTypeVo("Successfully registered for cancel: " + lraInstanceCancelRequestTypeVo));
    }

    /**
     * this will register an applicant (which is a compensation action of a service)
     * to a specific LRA instance. In case of LRA cancel action, all the
     * applicants registered to it will be notified
     *
     * @param lraApplicantVo body of request which is the details about applicant
     * @return failure or success message based on HttpStatus
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/applicant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAApplicantRegisterResponseTypeVo> registerApplicant(@RequestBody LRAApplicantVo lraApplicantVo) throws LRAException.InstanceNotFoundException, LRARequestException.InternalException {
        LRAApplicantEntity lraApplicantEntity = lraApplicantService.registerLRAApplicant(lraApplicantVo);
        //cancelHandlerService.doCompensation(lraApplicantEntity);//TODO for testing purpose
        return ResponseEntity.ok(new LRAApplicantRegisterResponseTypeVo("Successfully registered: " + lraApplicantVo));
    }

    /**
     * for testing purpose, to get a JSON format of a LRAApplicantVo object
     *
     * @return JSON format of LRAApplicantVo object
     */
    @GetMapping(value = "/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAApplicantVo> getApplicant() {
        LRAApplicantVo lraApplicantVo = new LRAApplicantVo();
        lraApplicantVo.setAppName("state-machine");
        lraApplicantVo.setHttpMethod("GET");
        lraApplicantVo.setLraInstanceEntityUUID("this-is-sample-uuid");
        lraApplicantVo.setPathVariables("123/456/789");
        lraApplicantVo.setServiceName("state-machine-health-v1");
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("param1", "value1");
        lraApplicantVo.setRequestParameters(requestParameters);
        lraApplicantVo.setRequestBodyInJSON("{ \"factoryName\" : \"type1\" }");
        lraApplicantVo.setLraApplicantStatus(LRAApplicantStatus.REGISTERED);
        return ResponseEntity.ok(lraApplicantVo);
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

    @Autowired
    @Qualifier(CancelHandlerServiceImpl.BEAN_NAME)
    public void setCancelHandlerService(CancelHandlerService cancelHandlerService) {
        this.cancelHandlerService = cancelHandlerService;
    }
}
