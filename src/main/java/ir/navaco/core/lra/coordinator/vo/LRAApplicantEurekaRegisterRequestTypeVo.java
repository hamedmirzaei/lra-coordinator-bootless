package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;
import java.util.Map;

public class LRAApplicantEurekaRegisterRequestTypeVo implements Serializable {

    private String lraInstanceEntityUUID;
    private String appName;
    private String serviceName;
    private String httpMethod;
    private String pathVariables;
    private Map<String, String> requestParameters;
    private String requestBodyInJSON;
    private Integer connectTimeout;
    private Integer readTimeout;

    public LRAApplicantEurekaRegisterRequestTypeVo() {
    }

    public LRAApplicantEurekaRegisterRequestTypeVo(String lraInstanceEntityUUID, String appName, String serviceName, String httpMethod, String pathVariables, Map<String, String> requestParameters, String requestBodyInJSON, Integer connectTimeout, Integer readTimeout) {
        this.lraInstanceEntityUUID = lraInstanceEntityUUID;
        this.appName = appName;
        this.serviceName = serviceName;
        this.httpMethod = httpMethod;
        this.pathVariables = pathVariables;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(String pathVariables) {
        this.pathVariables = pathVariables;
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
                ", appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", pathVariables='" + pathVariables + '\'' +
                ", requestParameters=" + requestParameters +
                ", requestBodyInJSON='" + requestBodyInJSON + '\'' +
                ", connectTimeout=" + connectTimeout +
                ", readTimeout=" + readTimeout +
                '}';
    }
}
