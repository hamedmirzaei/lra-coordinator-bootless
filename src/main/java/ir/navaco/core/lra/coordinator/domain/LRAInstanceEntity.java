package ir.navaco.core.lra.coordinator.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.enums.Schema;
import ir.navaco.core.lra.coordinator.enums.converter.LRAInstanceStatusConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LRAInstanceEntity.LRA_INSTANCE_TABLE_NAME, schema = Schema.IF)
public class LRAInstanceEntity implements Serializable {

    public static final String LRA_INSTANCE_TABLE_NAME = "LRA_INSTANCE";
    public static final String LRA_INSTANCE_SEQUENCE_NAME = LRA_INSTANCE_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lra_ins_generator")
    @SequenceGenerator(name = "lra_ins_generator", sequenceName = LRAInstanceEntity.LRA_INSTANCE_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "UUID", nullable = false)
    private String uuid;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = LRAInstanceStatusConverter.class)
    private LRAInstanceStatus lraInstanceStatus;

    @Column(name = "RETRY_LIMIT", nullable = false)
    private Integer retryLimit;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lraInstanceEntity")
    private List<LRAApplicantEntity> lraApplicantEntities;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lraInstanceEntity")
    private List<LRAInstanceExecutionEntity> lraInstanceExecutionEntities;

    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false, name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public LRAInstanceEntity() {
    }

    public LRAInstanceEntity(String uuid, LRAInstanceStatus lraInstanceStatus, Integer retryLimit, List<LRAApplicantEntity> lraApplicantEntities, List<LRAInstanceExecutionEntity> lraInstanceExecutionEntities) {
        this.uuid = uuid;
        this.lraInstanceStatus = lraInstanceStatus;
        this.retryLimit = retryLimit;
        this.lraApplicantEntities = lraApplicantEntities;
        this.lraInstanceExecutionEntities = lraInstanceExecutionEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LRAInstanceStatus getLraInstanceStatus() {
        return lraInstanceStatus;
    }

    public void setLraInstanceStatus(LRAInstanceStatus lraInstanceStatus) {
        this.lraInstanceStatus = lraInstanceStatus;
    }

    public Integer getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(Integer retryLimit) {
        this.retryLimit = retryLimit;
    }

    public List<LRAApplicantEntity> getLraApplicantEntities() {
        return lraApplicantEntities;
    }

    public void setLraApplicantEntities(List<LRAApplicantEntity> lraApplicantEntities) {
        this.lraApplicantEntities = lraApplicantEntities;
    }

    public List<LRAInstanceExecutionEntity> getLraInstanceExecutionEntities() {
        return lraInstanceExecutionEntities;
    }

    public void setLraInstanceExecutionEntities(List<LRAInstanceExecutionEntity> lraInstanceExecutionEntities) {
        this.lraInstanceExecutionEntities = lraInstanceExecutionEntities;
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
        return "LRAInstanceEntity{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", lraInstanceStatus=" + lraInstanceStatus +
                ", retryLimit=" + retryLimit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
