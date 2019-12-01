package ir.navaco.core.lra.coordinator.domain;

import ir.navaco.core.lra.coordinator.enums.LRAInstanceStatus;
import ir.navaco.core.lra.coordinator.enums.Schema;
import ir.navaco.core.lra.coordinator.enums.converter.LRAInstanceStatusConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = LRAInstanceExecutionEntity.LRA_INSTANCE_EXECUTION_TABLE_NAME, schema = Schema.IF)
public class LRAInstanceExecutionEntity implements Serializable {

    public static final String LRA_INSTANCE_EXECUTION_TABLE_NAME = "LRA_INSTANCE_EXECUTION";
    public static final String LRA_INSTANCE_EXECUTION_SEQUENCE_NAME = LRA_INSTANCE_EXECUTION_TABLE_NAME + "_SEQ";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lra_ins_exc_generator")
    @SequenceGenerator(name = "lra_ins_exc_generator", sequenceName = LRAInstanceExecutionEntity.LRA_INSTANCE_EXECUTION_SEQUENCE_NAME, schema = Schema.IF, allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "STATUS", nullable = false)
    @Convert(converter = LRAInstanceStatusConverter.class)
    private LRAInstanceStatus lraInstanceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LRA_INSTANCE", referencedColumnName = "ID")
    private LRAInstanceEntity lraInstanceEntity;

    public LRAInstanceExecutionEntity() {
    }

    public LRAInstanceExecutionEntity(LocalDateTime startDate, LRAInstanceEntity lraInstanceEntity) {
        this.startDate = startDate;
        this.lraInstanceEntity = lraInstanceEntity;
    }

    public LRAInstanceExecutionEntity(LocalDateTime startDate, LocalDateTime endDate, LRAInstanceStatus lraInstanceStatus, LRAInstanceEntity lraInstanceEntity) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lraInstanceStatus = lraInstanceStatus;
        this.lraInstanceEntity = lraInstanceEntity;
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

    public LRAInstanceStatus getLraInstanceStatus() {
        return lraInstanceStatus;
    }

    public void setLraInstanceStatus(LRAInstanceStatus lraInstanceStatus) {
        this.lraInstanceStatus = lraInstanceStatus;
    }

    public LRAInstanceEntity getLraInstanceEntity() {
        return lraInstanceEntity;
    }

    public void setLraInstanceEntity(LRAInstanceEntity lraInstanceEntity) {
        this.lraInstanceEntity = lraInstanceEntity;
    }

    @Override
    public String toString() {
        return "LRAInstanceExecutionEntity{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lraInstanceStatus=" + lraInstanceStatus +
                ", lraInstanceEntity=" + lraInstanceEntity +
                '}';
    }

}
