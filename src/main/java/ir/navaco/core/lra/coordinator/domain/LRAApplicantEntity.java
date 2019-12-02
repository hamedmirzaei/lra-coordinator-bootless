package ir.navaco.core.lra.coordinator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.LRAApplicantType;
import ir.navaco.core.lra.coordinator.enums.Schema;
import ir.navaco.core.lra.coordinator.enums.converter.LRAApplicantStatusConverter;
import ir.navaco.core.lra.coordinator.enums.converter.LRAApplicantTypeConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Column(name = "APP_NAME")
    private String appName;

    @Column(name = "SERVICE_NAME")
    private String serviceName;

    @Column(name = "BASE_URL")
    private String baseUrl;

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

    @Column(name = "CONNECT_TIMEOUT")
    private Integer connectTimeout;

    @Column(name = "READ_TIMEOUT")
    private Integer readTimeout;

    @Column(name = "TYPE", nullable = false)
    @Convert(converter = LRAApplicantTypeConverter.class)
    private LRAApplicantType lraApplicantType;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lraApplicantEntity")
    private List<LRAApplicantExecutionEntity> lraApplicantExecutionEntities;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

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

    public LRAApplicantType getLraApplicantType() {
        return lraApplicantType;
    }

    public void setLraApplicantType(LRAApplicantType lraApplicantType) {
        this.lraApplicantType = lraApplicantType;
    }

    public List<LRAApplicantExecutionEntity> getLraApplicantExecutionEntities() {
        return lraApplicantExecutionEntities;
    }

    public void setLraApplicantExecutionEntities(List<LRAApplicantExecutionEntity> lraApplicantExecutionEntities) {
        this.lraApplicantExecutionEntities = lraApplicantExecutionEntities;
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
                ", lraInstanceEntity=" + lraInstanceEntity +
                ", appName='" + appName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", pathVariables='" + pathVariables + '\'' +
                ", requestParameters='" + requestParameters + '\'' +
                ", requestBodyInJSON='" + requestBodyInJSON + '\'' +
                ", lraApplicantStatus=" + lraApplicantStatus +
                ", connectTimeout=" + connectTimeout +
                ", readTimeout=" + readTimeout +
                ", lraApplicantType=" + lraApplicantType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
