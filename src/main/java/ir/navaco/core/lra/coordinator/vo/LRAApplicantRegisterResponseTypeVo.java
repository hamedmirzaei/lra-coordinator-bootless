package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAApplicantRegisterResponseTypeVo implements Serializable {

    private String messageDescription;

    public LRAApplicantRegisterResponseTypeVo() {
    }

    public LRAApplicantRegisterResponseTypeVo(String messageDescription) {
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
        return "LRAApplicantRegisterResponseTypeVo{" +
                "messageDescription='" + messageDescription + '\'' +
                '}';
    }
}
