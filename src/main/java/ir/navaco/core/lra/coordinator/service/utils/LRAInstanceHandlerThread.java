package ir.navaco.core.lra.coordinator.service.utils;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAApplicantExecutionEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceExecutionEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.service.LRAApplicantExecutionService;
import ir.navaco.core.lra.coordinator.service.LRAApplicantService;
import ir.navaco.core.lra.coordinator.service.LRAInstanceExecutionService;
import ir.navaco.core.lra.coordinator.service.LRAInstanceService;
import ir.navaco.core.lra.coordinator.utils.Constants;
import ir.navaco.core.lra.coordinator.utils.HttpUtils;
import ir.navaco.core.lra.coordinator.utils.MapUtils;
import ir.navaco.core.lra.coordinator.vo.LRAApplicantCompensationResponseTypeVo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Callable;

public class LRAInstanceHandlerThread implements Callable<Boolean> {

    private LRAInstanceService lraInstanceService;
    private LRAInstanceExecutionService lraInstanceExecutionService;
    private LRAApplicantService lraApplicantService;
    private LRAApplicantExecutionService lraApplicantExecutionService;
    private LRAInstanceEntity lraInstanceEntity;

    public LRAInstanceHandlerThread(LRAInstanceService lraInstanceService, LRAInstanceExecutionService lraInstanceExecutionService, LRAApplicantService lraApplicantService, LRAApplicantExecutionService lraApplicantExecutionService, LRAInstanceEntity lraInstanceEntity) {
        this.lraInstanceService = lraInstanceService;
        this.lraInstanceExecutionService = lraInstanceExecutionService;
        this.lraApplicantService = lraApplicantService;
        this.lraApplicantExecutionService = lraApplicantExecutionService;
        this.lraInstanceEntity = lraInstanceEntity;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            lraInstanceEntity.setLraInstanceStatus(LRAInstanceStatus.CANCELING);
            lraInstanceService.updateLRAInstance(lraInstanceEntity);
            if (doCompensation(lraInstanceEntity)) {
                return true;
            }
        } catch (SystemException.InternalException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * this method tries to call all rest requests associated with
     * all applicants of input LRAInstance
     *
     * @param lraInstanceEntity contains all the info about configs and applicants of LRAInstance
     * @return true in case of success and false otherwise
     */
    private Boolean doCompensation(LRAInstanceEntity lraInstanceEntity) {
        for (int i = 0; i < lraInstanceEntity.getRetryLimit(); i++) {
            LRAInstanceExecutionEntity lraInstanceExecutionEntity = new LRAInstanceExecutionEntity(LocalDateTime.now(), lraInstanceEntity);
            Boolean done = true;
            for (LRAApplicantEntity lraApplicantEntity : lraInstanceEntity.getLraApplicantEntities()) {
                if (!lraApplicantEntity.getLraApplicantStatus().equals(LRAApplicantStatus.ACKNOWLEDGED)) {
                    try {
                        if (doCompensation(lraApplicantEntity)) {
                            lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.ACKNOWLEDGED);
                        } else {
                            lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.FAILED);
                            done = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.FAILED);
                        done = false;
                    }
                    try {
                        lraApplicantService.updateLRAApplicant(lraApplicantEntity);
                    } catch (SystemException.InternalException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (done) {
                successLRAInstanceExecutionEntity(lraInstanceExecutionEntity);
                return true;
            } else {
                failedLRAInstanceExecutionEntity(lraInstanceExecutionEntity);
            }
        }
        return false;
    }

    /**
     * this method uses RestTemplate to call rest request
     *
     * @param lraApplicantEntity contains all the info about rest provider
     * @return true in case of success and false otherwise
     */
    public Boolean doCompensation(LRAApplicantEntity lraApplicantEntity) {
        LRAApplicantExecutionEntity lraApplicantExecutionEntity = new LRAApplicantExecutionEntity(LocalDateTime.now(), lraApplicantEntity);
        try {
            RestTemplate restTemplate = restTemplate(lraApplicantEntity.getConnectTimeout(), lraApplicantEntity.getReadTimeout());

            String url = "";
            //set appName, serviceName and PathVariables
            if (lraApplicantEntity.getAppName() == null || lraApplicantEntity.getServiceName() == null ||
                    "".equals(lraApplicantEntity.getAppName()) || "".equals(lraApplicantEntity.getServiceName())) {
                url = lraApplicantEntity.getBaseUrl();
            } else {
                url = Constants.eurekaProperties.getEdgeServerURL() + lraApplicantEntity.getAppName() + "/" + lraApplicantEntity.getServiceName();
                if (lraApplicantEntity.getPathVariables() != null && !lraApplicantEntity.getPathVariables().equals(""))
                    url = url + "/" + lraApplicantEntity.getPathVariables();
            }

            //set query params
            url = appendRequestParams(lraApplicantEntity.getRequestParameters(), url);

            HttpMethod method = HttpMethod.resolve(lraApplicantEntity.getHttpMethod());
            HttpEntity<String> requestEntity = HttpUtils.createHeader(lraApplicantEntity.getRequestBodyInJSON(), method);

            ResponseEntity<LRAApplicantCompensationResponseTypeVo> response = restTemplate.exchange(
                    url,
                    method,
                    requestEntity,
                    new ParameterizedTypeReference<LRAApplicantCompensationResponseTypeVo>() {
                    });
            //if response.messageCode = 'LRA-0000' then every thing is Ok, not otherwise
            //remember that compensation actions should not return anything, they
            //are just business code which we expect to return a StatusCode of 200
            //or something else (like 422)
            lraApplicantExecutionEntity.setMessage(response.getBody().getMessageCode());
            if ("LRA-0000".equals(response.getBody().getMessageCode())) {
                successLRAApplicantExecutionEntity(lraApplicantExecutionEntity);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            lraApplicantExecutionEntity.setMessage(e.getMessage());
        }
        failedLRAApplicantExecutionEntity(lraApplicantExecutionEntity);
        return false;
    }

    private String appendRequestParams(String requestParams, String url) {
        if (requestParams != null && !"".equals(requestParams)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            for (Map.Entry<String, String> param : MapUtils.stringToMap(requestParams).entrySet()) {
                builder.queryParam(param.getKey(), param.getValue());
            }
            url = builder.toUriString();
        }
        return url;
    }

    private void failedLRAInstanceExecutionEntity(LRAInstanceExecutionEntity lraInstanceExecutionEntity) {
        lraInstanceExecutionEntity.setEndDate(LocalDateTime.now());
        lraInstanceExecutionEntity.setLraInstanceStatus(LRAInstanceStatus.FAILED);
        saveLRAInstanceExecutionEntity(lraInstanceExecutionEntity);
    }

    private void successLRAInstanceExecutionEntity(LRAInstanceExecutionEntity lraInstanceExecutionEntity) {
        lraInstanceExecutionEntity.setEndDate(LocalDateTime.now());
        lraInstanceExecutionEntity.setLraInstanceStatus(LRAInstanceStatus.CANCELED);
        saveLRAInstanceExecutionEntity(lraInstanceExecutionEntity);
    }

    private void saveLRAInstanceExecutionEntity(LRAInstanceExecutionEntity lraApplicantExecutionEntity) {
        lraInstanceExecutionService.saveLRAInstanceExecution(lraApplicantExecutionEntity);
    }

    private void failedLRAApplicantExecutionEntity(LRAApplicantExecutionEntity lraApplicantExecutionEntity) {
        lraApplicantExecutionEntity.setEndDate(LocalDateTime.now());
        lraApplicantExecutionEntity.setLraApplicantStatus(LRAApplicantStatus.FAILED);
        saveLRAApplicantExecutionEntity(lraApplicantExecutionEntity);
    }

    private void successLRAApplicantExecutionEntity(LRAApplicantExecutionEntity lraApplicantExecutionEntity) {
        lraApplicantExecutionEntity.setEndDate(LocalDateTime.now());
        lraApplicantExecutionEntity.setLraApplicantStatus(LRAApplicantStatus.ACKNOWLEDGED);
        saveLRAApplicantExecutionEntity(lraApplicantExecutionEntity);
    }

    private void saveLRAApplicantExecutionEntity(LRAApplicantExecutionEntity lraApplicantExecutionEntity) {
        lraApplicantExecutionService.saveLRAApplicantExecution(lraApplicantExecutionEntity);
    }

    public RestTemplate restTemplate(Integer connectTimeout, Integer readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
