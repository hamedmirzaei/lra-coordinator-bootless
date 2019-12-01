package ir.navaco.core.lra.coordinator.api;

import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.service.*;
import ir.navaco.core.lra.coordinator.utils.HttpUtils;
import ir.navaco.core.lra.coordinator.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public ResponseEntity<LRAResponseVo> createLRA(@RequestBody LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo) throws SystemException.InternalException {
        LRAInstanceEntity lraInstanceEntity = lraInstanceService.createLRAInstance(lraInstanceCreateRequestTypeVo);
        LRAInstanceCreateResponseTypeVo result = new LRAInstanceCreateResponseTypeVo(lraInstanceEntity.getUuid());
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * this will cancel a LRA instance
     *
     * @param lraInstanceCancelRequestTypeVo request type
     * @return message for failed and successful execution
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/instance/cancel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAResponseVo> cancelLRA(@RequestBody LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo) throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyCanceledException, LRAException.InstanceUnderCancelException {
        lraInstanceService.cancelLRAInstance(lraInstanceCancelRequestTypeVo);
        LRAInstanceCancelResponseTypeVo result = new LRAInstanceCancelResponseTypeVo("Successfully registered for cancel: " + lraInstanceCancelRequestTypeVo);
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * this will register an applicant (which is a compensation action of a service)
     * to a specific LRA instance. In case of LRA cancel action, all the
     * applicants registered to it will be notified
     *
     * @param lraApplicantRegisterRequestTypeVo body of request which is the details about applicant
     * @return failure or success message based on HttpStatus
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/applicant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAResponseVo> registerApplicant(@RequestBody LRAApplicantRegisterRequestTypeVo lraApplicantRegisterRequestTypeVo) throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        lraApplicantService.registerLRAApplicant(lraApplicantRegisterRequestTypeVo);
        LRAApplicantRegisterResponseTypeVo result = new LRAApplicantRegisterResponseTypeVo("Successfully registered: " + lraApplicantRegisterRequestTypeVo);
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * for testing purpose, to get a JSON format of a LRAApplicantRegisterRequestTypeVo object
     *
     * @return JSON format of LRAApplicantRegisterRequestTypeVo object
     */
    @GetMapping(value = "/applicant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAApplicantRegisterRequestTypeVo> getApplicant() {
        LRAApplicantRegisterRequestTypeVo lraApplicantRegisterRequestTypeVo = new LRAApplicantRegisterRequestTypeVo();
        lraApplicantRegisterRequestTypeVo.setAppName("state-machine");
        lraApplicantRegisterRequestTypeVo.setHttpMethod("GET");
        lraApplicantRegisterRequestTypeVo.setLraInstanceEntityUUID("this-is-sample-uuid");
        lraApplicantRegisterRequestTypeVo.setPathVariables("123/456/789");
        lraApplicantRegisterRequestTypeVo.setServiceName("state-machine-health-v1");
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("param1", "value1");
        lraApplicantRegisterRequestTypeVo.setRequestParameters(requestParameters);
        lraApplicantRegisterRequestTypeVo.setRequestBodyInJSON("{ \"factoryName\" : \"type1\" }");
        lraApplicantRegisterRequestTypeVo.setConnectTimeout(20000);
        lraApplicantRegisterRequestTypeVo.setReadTimeout(20000);
        return ResponseEntity.ok(lraApplicantRegisterRequestTypeVo);
    }

    /**
     * this is for testing purposes
     *
     * @return
     */
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object test() {
        String json = "{\n" +
                "  \"lraInstanceEntityUUID\": \"this-is-sample-uuid\",\n" +
                "  \"appName\": \"state-machine\",\n" +
                "  \"serviceName\": \"state-machine-health-v1\",\n" +
                "  \"httpMethod\": \"GET\",\n" +
                "  \"pathVariables\": \"123/456/789\",\n" +
                "  \"requestParameters\": {\n" +
                "    \"param1\": \"value1\"\n" +
                "  },\n" +
                "  \"requestBodyInJSON\": \"{ \\\"factoryName\\\" : \\\"type1\\\" }\",\n" +
                "  \"lraApplicantStatus\": \"REGISTERED\",\n" +
                "  \"connectTimeout\" : \"20000\",\n" +
                "  \"readTimeout\" : \"20000\"\n" +
                "}";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = HttpUtils.createHeader(json, HttpMethod.POST);
        ResponseEntity<LRAResponseVo> response = restTemplate.exchange(
                "http://localhost:8085/lra-coordinator/applicant",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<LRAResponseVo>() {
                });
        return response;
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
