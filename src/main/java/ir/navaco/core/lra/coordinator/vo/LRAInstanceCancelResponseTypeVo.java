package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAInstanceCancelResponseTypeVo implements Serializable {

    private String messageDescription;

    public LRAInstanceCancelResponseTypeVo() {
    }

    public LRAInstanceCancelResponseTypeVo(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    @Override
    public String toString() {
        return "LRAInstanceCancelResponseTypeVo{" +
                "messageDescription='" + messageDescription + '\'' +
                '}';
    }
}
