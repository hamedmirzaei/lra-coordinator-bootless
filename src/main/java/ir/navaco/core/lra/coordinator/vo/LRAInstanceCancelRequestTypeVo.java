package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAInstanceCancelRequestTypeVo implements Serializable {

    private String uuid;

    public LRAInstanceCancelRequestTypeVo() {
    }

    public LRAInstanceCancelRequestTypeVo(String uuid) {
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
        return "LRAInstanceCancelRequestTypeVo{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
