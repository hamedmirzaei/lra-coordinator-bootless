package ir.navaco.core.lra.coordinator.vo;

public class LRAInstanceCreateResponseTypeVo {
    private String uuid;

    public LRAInstanceCreateResponseTypeVo() {
    }

    public LRAInstanceCreateResponseTypeVo(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "LRAInstanceCreateResponseTypeVo{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
