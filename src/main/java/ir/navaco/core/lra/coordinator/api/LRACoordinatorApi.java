package ir.navaco.core.lra.coordinator.api;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.exception.LRAException;
import ir.navaco.core.lra.coordinator.exception.LRARequestException;
import ir.navaco.core.lra.coordinator.service.LRAApplicantService;
import ir.navaco.core.lra.coordinator.service.LRAApplicantServiceImpl;
import ir.navaco.core.lra.coordinator.service.LRAInstanceService;
import ir.navaco.core.lra.coordinator.service.LRAInstanceServiceImpl;
import ir.navaco.core.lra.coordinator.utils.HttpUtils;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class LRACoordinatorApi {

    //TODO cancel all pending cancel requests at the startup of the application
    //there should be one background thread that periodically checks for new
    //cancel requests and based on retry-limit and timeout, do the proper action


    private LRAInstanceService lraInstanceService;
    private LRAApplicantService lraApplicantService;

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
     * @param input list of params, for example timeout or retry
     * @return UUID of LRA instance or failure message based on HttpStatus
     * @throws LRARequestException.FieldNotExist
     * @throws LRARequestException.BadSizeMap
     */
    @PostMapping(value = "/instance", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createLRA(@RequestBody Map<String, String> input) throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap {
        LRAInstanceEntity lraInstanceEntity = lraInstanceService.saveLRAInstance(input);
        return ResponseEntity.ok(lraInstanceEntity.getUuid());
    }

    /**
     * this will cancel a LRA instance
     *
     * @param input
     * @return
     * @throws LRARequestException.FieldNotExist
     * @throws LRARequestException.BadSizeMap
     * @throws LRAException.InstanceNotFoundException
     */
    @PostMapping(value = "/instance/cancel", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelLRA(@RequestBody Map<String, String> input) throws LRARequestException.FieldNotExist, LRARequestException.BadSizeMap, LRAException.InstanceNotFoundException {
        lraInstanceService.cancelLRAInstance(input);
        return ResponseEntity.ok("Successfully canceled");
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
    public ResponseEntity<String> registerApplicant(@RequestBody LRAApplicantVo lraApplicantVo) throws LRAException.InstanceNotFoundException {
        LRAApplicantEntity lraApplicantEntity = lraApplicantService.registerLRAApplicant(lraApplicantVo);
        doCompensation(lraApplicantEntity);//TODO it should be removed
        return ResponseEntity.ok("Successfully registered");
    }

    private void doCompensation(LRAApplicantEntity lraApplicantEntity) {
        RestTemplate restTemplate = new RestTemplate();

        //set appName and serviceName
        String url = "http://localhost:8888/edge-server/" + lraApplicantEntity.getAppName() + "/" + lraApplicantEntity.getServiceName();

        //set path variables
        if (lraApplicantEntity.getPathVariables() != null && !lraApplicantEntity.getPathVariables().equals(""))
            url = url + "/" + lraApplicantEntity.getPathVariables();

        //set query params
        if (lraApplicantEntity.getRequestParameters() != null && !lraApplicantEntity.getRequestParameters().equals("")) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            for (Map.Entry<String, String> param : MapUtils.stringToMap(lraApplicantEntity.getRequestParameters()).entrySet()) {
                builder.queryParam(param.getKey(), param.getValue());
            }
            url = builder.toUriString();
        }

        HttpMethod method = HttpMethod.resolve(lraApplicantEntity.getHttpMethod());
        HttpEntity<String> requestEntity = HttpUtils.createHeader(lraApplicantEntity.getRequestBodyInJSON(), method);

        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                method,
                requestEntity,
                new ParameterizedTypeReference<Object>() {
                });
        //TODO if response.StatusCode = 200 then every thing is Ok, not otherwise
        //remember that compensation actions should not return anything, they
        //are just business code which we expect to return a StatusCode of 200
        //or something else (like 422)
        if (response.getStatusCode() == HttpStatus.OK) {
            //done: update status
            lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.ACKNOWLEDGED);
            lraApplicantService.updateLRAApplicant(lraApplicantEntity);
        } else {
            //retry policy
        }

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

}
