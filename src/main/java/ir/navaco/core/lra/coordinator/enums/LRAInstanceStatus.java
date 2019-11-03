package ir.navaco.core.lra.coordinator.enums;

public enum LRAInstanceStatus {
    CREATED("CREATED"),
    CANCEL_REQUEST("CANCEL_REQUEST"),
    CANCELING("CANCELING"),
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED");

    private String statusName;

    LRAInstanceStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
