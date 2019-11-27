package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAApplicantCompensationResponseTypeVo implements Serializable {

    /**
     * value = "LRA-0000" means success and otherwise fail
     */
    private String messageCode;

    public LRAApplicantCompensationResponseTypeVo() {
    }

    public LRAApplicantCompensationResponseTypeVo(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String toString() {
        return "LRAApplicantCompensationResponseTypeVo{" +
                "messageCode='" + messageCode + '\'' +
                '}';
    }
}
