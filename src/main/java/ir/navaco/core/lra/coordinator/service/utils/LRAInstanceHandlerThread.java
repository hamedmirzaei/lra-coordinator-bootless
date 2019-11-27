package ir.navaco.core.lra.coordinator.service.utils;

import ir.navaco.core.lra.coordinator.domain.LRAApplicantEntity;
import ir.navaco.core.lra.coordinator.domain.LRAInstanceEntity;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.exception.SystemException;
import ir.navaco.core.lra.coordinator.service.LRAApplicantService;
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

import java.util.Map;
import java.util.concurrent.Callable;

public class LRAInstanceHandlerThread implements Callable<Boolean> {

    private LRAInstanceService lraInstanceService;
    private LRAApplicantService lraApplicantService;
    private LRAInstanceEntity lraInstanceEntity;

    public LRAInstanceHandlerThread(LRAInstanceService lraInstanceService, LRAApplicantService lraApplicantService, LRAInstanceEntity lraInstanceEntity) {
        this.lraInstanceService = lraInstanceService;
        this.lraApplicantService = lraApplicantService;
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

    private Boolean doCompensation(LRAInstanceEntity lraInstanceEntity) {
        for (int i = 0; i < lraInstanceEntity.getRetryLimit(); i++) {
            Boolean done = true;
            for (LRAApplicantEntity lraApplicantEntity : lraInstanceEntity.getLraApplicantEntities()) {
                if (lraApplicantEntity.getLraApplicantStatus().equals(LRAApplicantStatus.REGISTERED)) {
                    try {
                        if (doCompensation(lraApplicantEntity)) {
                            lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.ACKNOWLEDGED);
                        } else {
                            lraApplicantEntity.setLraApplicantStatus(LRAApplicantStatus.FAILED);
                            done = false;
                        }
                        lraApplicantService.updateLRAApplicant(lraApplicantEntity);
                    } catch (Exception e) {
                        e.printStackTrace();
                        done = false;
                    }
                }
            }
            if (done)
                return true;
        }
        return false;
    }

    public boolean doCompensation(LRAApplicantEntity lraApplicantEntity) {
        try {
            RestTemplate restTemplate = restTemplate(lraApplicantEntity.getConnectTimeout(), lraApplicantEntity.getReadTimeout());

            //set appName and serviceName
            String url = Constants.eurekaProperties.getEdgeServerURL() + lraApplicantEntity.getAppName() + "/" + lraApplicantEntity.getServiceName();

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
            if ("LRA-0000".equals(response.getBody().getMessageCode())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public RestTemplate restTemplate(Integer connectTimeout, Integer readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        return new RestTemplate(factory);
    }
}
