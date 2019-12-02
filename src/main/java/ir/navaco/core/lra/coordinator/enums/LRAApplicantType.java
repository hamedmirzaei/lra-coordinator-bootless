package ir.navaco.core.lra.coordinator.enums;

public enum LRAApplicantType {
    EUREKA("EUREKA"),
    DIRECT("DIRECT");

    private String typeName;

    LRAApplicantType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
