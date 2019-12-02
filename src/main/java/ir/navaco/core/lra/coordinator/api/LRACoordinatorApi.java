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
    public ResponseEntity<LRAResponseVo> createLRAInstance(@RequestBody LRAInstanceCreateRequestTypeVo lraInstanceCreateRequestTypeVo) throws SystemException.InternalException {
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
    public ResponseEntity<LRAResponseVo> cancelLRAInstance(@RequestBody LRAInstanceCancelRequestTypeVo lraInstanceCancelRequestTypeVo) throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyCanceledException, LRAException.InstanceUnderCancelException {
        lraInstanceService.cancelLRAInstance(lraInstanceCancelRequestTypeVo);
        LRAInstanceCancelResponseTypeVo result = new LRAInstanceCancelResponseTypeVo("Successfully registered for cancel: " + lraInstanceCancelRequestTypeVo);
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * this will register an applicant (which is a compensation action of a service)
     * to a specific LRA instance. In case of LRA cancel action, all the
     * applicants registered to it will be notified
     *
     * @param lraApplicantDirectRegisterRequestTypeVo body of request which is the details about applicant
     * @return failure or success message based on HttpStatus
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/directapplicant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAResponseVo> registerDirectLRAApplicant(@RequestBody LRAApplicantDirectRegisterRequestTypeVo lraApplicantDirectRegisterRequestTypeVo)
            throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        lraApplicantService.registerDirectLRAApplicant(lraApplicantDirectRegisterRequestTypeVo);
        LRAApplicantRegisterResponseTypeVo result = new LRAApplicantRegisterResponseTypeVo("Successfully registered: " + lraApplicantDirectRegisterRequestTypeVo);
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * this will register an applicant (which is a compensation action of a service)
     * to a specific LRA instance. In case of LRA cancel action, all the
     * applicants registered to it will be notified
     *
     * @param lraApplicantEurekaRegisterRequestTypeVo body of request which is the details about applicant
     * @return failure or success message based on HttpStatus
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/eurekaapplicant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAResponseVo> registerEurekaLRAApplicant(@RequestBody LRAApplicantEurekaRegisterRequestTypeVo lraApplicantEurekaRegisterRequestTypeVo) throws LRAException.InstanceNotFoundException, SystemException.InternalException, LRAException.InstanceAlreadyProcessedException {
        lraApplicantService.registerEurekaLRAApplicant(lraApplicantEurekaRegisterRequestTypeVo);
        LRAApplicantRegisterResponseTypeVo result = new LRAApplicantRegisterResponseTypeVo("Successfully registered: " + lraApplicantEurekaRegisterRequestTypeVo);
        return ResponseEntity.ok(new LRAResponseVo(result));
    }

    /**
     * for testing purpose, to get a JSON format of a LRAApplicantDirectRegisterRequestTypeVo object
     *
     * @return JSON format of LRAApplicantDirectRegisterRequestTypeVo object
     */
    @GetMapping(value = "/directapplicant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAApplicantDirectRegisterRequestTypeVo> getDirectLRAApplicant() {
        LRAApplicantDirectRegisterRequestTypeVo lraApplicantDirectRegisterRequestTypeVo = new LRAApplicantDirectRegisterRequestTypeVo();
        lraApplicantDirectRegisterRequestTypeVo.setLraInstanceEntityUUID("this-is-sample-uuid");
        lraApplicantDirectRegisterRequestTypeVo.setBaseUrl("http://localhost:8082/state-machine/health");
        lraApplicantDirectRegisterRequestTypeVo.setHttpMethod("GET");
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("param1", "value1");
        lraApplicantDirectRegisterRequestTypeVo.setRequestParameters(requestParameters);
        lraApplicantDirectRegisterRequestTypeVo.setRequestBodyInJSON("{ \"factoryName\" : \"type1\" }");
        lraApplicantDirectRegisterRequestTypeVo.setConnectTimeout(20000);
        lraApplicantDirectRegisterRequestTypeVo.setReadTimeout(20000);
        return ResponseEntity.ok(lraApplicantDirectRegisterRequestTypeVo);
    }

    /**
     * for testing purpose, to get a JSON format of a LRAApplicantEurekaRegisterRequestTypeVo object
     *
     * @return JSON format of LRAApplicantEurekaRegisterRequestTypeVo object
     */
    @GetMapping(value = "/eurekaapplicant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LRAApplicantEurekaRegisterRequestTypeVo> getEurekaLRAApplicant() {
        LRAApplicantEurekaRegisterRequestTypeVo lraApplicantEurekaRegisterRequestTypeVo = new LRAApplicantEurekaRegisterRequestTypeVo();
        lraApplicantEurekaRegisterRequestTypeVo.setLraInstanceEntityUUID("this-is-sample-uuid");
        lraApplicantEurekaRegisterRequestTypeVo.setAppName("state-machine");
        lraApplicantEurekaRegisterRequestTypeVo.setServiceName("state-machine-health-v1");
        lraApplicantEurekaRegisterRequestTypeVo.setHttpMethod("GET");
        lraApplicantEurekaRegisterRequestTypeVo.setPathVariables("123/456/789");
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("param1", "value1");
        lraApplicantEurekaRegisterRequestTypeVo.setRequestParameters(requestParameters);
        lraApplicantEurekaRegisterRequestTypeVo.setRequestBodyInJSON("{ \"factoryName\" : \"type1\" }");
        lraApplicantEurekaRegisterRequestTypeVo.setConnectTimeout(20000);
        lraApplicantEurekaRegisterRequestTypeVo.setReadTimeout(20000);
        return ResponseEntity.ok(lraApplicantEurekaRegisterRequestTypeVo);
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
