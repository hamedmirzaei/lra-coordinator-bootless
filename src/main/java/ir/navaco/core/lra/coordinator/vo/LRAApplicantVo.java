package ir.navaco.core.lra.coordinator.vo;

import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;

import java.io.Serializable;
import java.util.Map;

public class LRAApplicantVo implements Serializable {

    private String lraInstanceEntityUUID;
    private String appName;
    private String serviceName;
    private String httpMethod;
    private String pathVariables;
    private Map<String, String> requestParameters;
    private String requestBodyInJSON;
    private LRAApplicantStatus lraApplicantStatus;

    public LRAApplicantVo() {
    }

    public LRAApplicantVo(String lraInstanceEntityUUID, String appName, String serviceName, String httpMethod, String pathVariables, Map<String, String> requestParameters, String requestBodyInJSON, LRAApplicantStatus lraApplicantStatus) {
        this.lraInstanceEntityUUID = lraInstanceEntityUUID;
        this.appName = appName;
        this.serviceName = serviceName;
        this.httpMethod = httpMethod;
        this.pathVariables = pathVariables;
        this.requestParameters = requestParameters;
        this.requestBodyInJSON = requestBodyInJSON;
        this.lraApplicantStatus = lraApplicantStatus;
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

    public LRAApplicantStatus getLraApplicantStatus() {
        return lraApplicantStatus;
    }

    public void setLraApplicantStatus(LRAApplicantStatus lraApplicantStatus) {
        this.lraApplicantStatus = lraApplicantStatus;
    }

    @Override
    public String toString() {
        return "LRAApplicantVo{" +
                "lraInstanceEntityUUID='" + lraInstanceEntityUUID + '\'' +
                ", appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", pathVariables='" + pathVariables + '\'' +
                ", requestParameters=" + requestParameters +
                ", requestBodyInJSON='" + requestBodyInJSON + '\'' +
                ", lraApplicantStatus=" + lraApplicantStatus +
                '}';
    }
}
