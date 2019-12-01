package ir.navaco.core.lra.coordinator.domain;

import ir.navaco.core.lra.coordinator.enums.LRAApplicantStatus;
import ir.navaco.core.lra.coordinator.enums.Schema;
import ir.navaco.core.lra.coordinator.enums.converter.LRAApplicantStatusConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LRAApplicantExecutionEntity.LRA_APPLICANT_EXECUTION_TABLE_NAME, schema = Schema.IF)
public class LRAApplicantExecutionEntity implements Serializable {

    public static final String LRA_APPLICANT_EXECUTION_TABLE_NAME = "LRA_APPLICANT_EXECUTION";
    public static final String LRA_APPLICANT_EXECUTION_SEQUENCE_NAME = LRA_APPLICANT_EXECUTION_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lra_aplc_exc_generator")
    @SequenceGenerator(name = "lra_aplc_exc_generator", sequenceName = LRAApplicantExecutionEntity.LRA_APPLICANT_EXECUTION_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = LRAApplicantStatusConverter.class)
    private LRAApplicantStatus lraApplicantStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LRA_APPLICANT", referencedColumnName = "ID")
    private LRAApplicantEntity lraApplicantEntity;

    @Column(name = "MESSAGE")
    private String message;

    public LRAApplicantExecutionEntity() {
    }

    public LRAApplicantExecutionEntity(LocalDateTime startDate, LRAApplicantEntity lraApplicantEntity) {
        this.startDate = startDate;
        this.lraApplicantEntity = lraApplicantEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LRAApplicantStatus getLraApplicantStatus() {
        return lraApplicantStatus;
    }

    public void setLraApplicantStatus(LRAApplicantStatus lraApplicantStatus) {
        this.lraApplicantStatus = lraApplicantStatus;
    }

    public LRAApplicantEntity getLraApplicantEntity() {
        return lraApplicantEntity;
    }

    public void setLraApplicantEntity(LRAApplicantEntity lraApplicantEntity) {
        this.lraApplicantEntity = lraApplicantEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LRAApplicantExecutionEntity{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lraApplicantStatus=" + lraApplicantStatus +
                ", lraApplicantEntity=" + lraApplicantEntity +
                ", message=" + message +
                '}';
    }

}
