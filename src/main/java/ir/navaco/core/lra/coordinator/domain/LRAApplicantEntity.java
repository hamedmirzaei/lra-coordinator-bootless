package ir.navaco.core.lra.coordinator.domain;

import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.enums.Schema;
import ir.navaco.core.lra.coordinator.enums.converter.LRAApplicantStatusConverter;
import ir.navaco.core.lra.coordinator.enums.converter.LRAInstanceStatusConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LRAApplicantEntity.LRA_APPLICANT_TABLE_NAME, schema = Schema.IF)
public class LRAApplicantEntity implements Serializable {

    public static final String LRA_APPLICANT_TABLE_NAME = "LRA_APPLICANT";
    public static final String LRA_APPLICANT_SEQUENCE_NAME = LRA_APPLICANT_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lra_aplc_generator")
    @SequenceGenerator(name = "lra_aplc_generator", sequenceName = LRAApplicantEntity.LRA_APPLICANT_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LRA_INSTANCE", referencedColumnName = "ID")
    private LRAInstanceEntity lraInstanceEntity;

    @Column(name = "APP_NAME", nullable = false)
    private String appName;

    @Column(name = "SERVICE_NAME", nullable = false)
    private String serviceName;

    @Column(name = "HTTP_METHOD", nullable = false)
    private String httpMethod;

    @Column(name = "PATH_VARIABLES")
    private String pathVariables;

    @Column(name = "REQUEST_PARAMETERS")
    private String requestParameters;

    @Column(name = "REQUEST_BODY")
    private String requestBodyInJSON;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = LRAApplicantStatusConverter.class)
    private LRAApplicantStatus lraApplicantStatus;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public LRAApplicantEntity() {
    }

    public LRAApplicantEntity(LRAInstanceEntity lraInstanceEntity, String appName, String serviceName, String httpMethod, String pathVariables, String requestParameters, String requestBodyInJSON, LRAApplicantStatus lraApplicantStatus) {
        this.lraInstanceEntity = lraInstanceEntity;
        this.appName = appName;
        this.serviceName = serviceName;
        this.httpMethod = httpMethod;
        this.pathVariables = pathVariables;
        this.requestParameters = requestParameters;
        this.requestBodyInJSON = requestBodyInJSON;
        this.lraApplicantStatus = lraApplicantStatus;
    }

    public LRAApplicantEntity(Long id, LRAInstanceEntity lraInstanceEntity, String appName, String serviceName, String httpMethod, String pathVariables, String requestParameters, String requestBodyInJSON, LRAApplicantStatus lraApplicantStatus) {
        this.id = id;
        this.lraInstanceEntity = lraInstanceEntity;
        this.appName = appName;
        this.serviceName = serviceName;
        this.httpMethod = httpMethod;
        this.pathVariables = pathVariables;
        this.requestParameters = requestParameters;
        this.requestBodyInJSON = requestBodyInJSON;
        this.lraApplicantStatus = lraApplicantStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LRAInstanceEntity getLraInstanceEntity() {
        return lraInstanceEntity;
    }

    public void setLraInstanceEntity(LRAInstanceEntity lraInstanceEntity) {
        this.lraInstanceEntity = lraInstanceEntity;
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

    public String getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(String requestParameters) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "LRAApplicantEntity{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", pathVariables='" + pathVariables + '\'' +
                ", requestParameters='" + requestParameters + '\'' +
                ", requestBodyInJSON='" + requestBodyInJSON + '\'' +
                ", lraApplicantStatus=" + lraApplicantStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
