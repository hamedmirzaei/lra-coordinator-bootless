package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;
import java.util.Map;

public class LRAApplicantDirectRegisterRequestTypeVo implements Serializable {

    private String lraInstanceEntityUUID;
    private String baseUrl;
    private String httpMethod;
    private Map<String, String> requestParameters;
    private String requestBodyInJSON;
    private Integer connectTimeout;
    private Integer readTimeout;

    public LRAApplicantDirectRegisterRequestTypeVo() {
    }

    public LRAApplicantDirectRegisterRequestTypeVo(String lraInstanceEntityUUID, String baseUrl, String httpMethod, Map<String, String> requestParameters, String requestBodyInJSON, Integer connectTimeout, Integer readTimeout) {
        this.lraInstanceEntityUUID = lraInstanceEntityUUID;
        this.baseUrl = baseUrl;
        this.httpMethod = httpMethod;
        this.requestParameters = requestParameters;
        this.requestBodyInJSON = requestBodyInJSON;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    public String getLraInstanceEntityUUID() {
        return lraInstanceEntityUUID;
    }

    public void setLraInstanceEntityUUID(String lraInstanceEntityUUID) {
        this.lraInstanceEntityUUID = lraInstanceEntityUUID;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String getRequestBodyInJSON() {
        return requestBodyInJSON;
    }

    public void setRequestBodyInJSON(String requestBodyInJSON) {
        this.requestBodyInJSON = requestBodyInJSON;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    @Override
    public String toString() {
        return "LRAApplicantEurekaRegisterRequestTypeVo{" +
                "lraInstanceEntityUUID='" + lraInstanceEntityUUID + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", requestParameters=" + requestParameters +
                ", requestBodyInJSON='" + requestBodyInJSON + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", readTimeout=" + readTimeout +
                '}';
    }
}
