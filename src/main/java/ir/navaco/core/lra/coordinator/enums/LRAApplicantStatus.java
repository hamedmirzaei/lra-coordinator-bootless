package ir.navaco.core.lra.coordinator.enums;

public enum LRAApplicantStatus {
    REGISTERED("REGISTERED"),
    ACKNOWLEDGED("ACKNOWLEDGED"),
    FAILED("FAILED");

    private String statusName;

    LRAApplicantStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
