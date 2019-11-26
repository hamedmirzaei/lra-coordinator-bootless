
package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAResponseVo implements Serializable {

    private String messageCode;
    private String messageDescription;
    private long messageId;
    private long numberValue;
    private Object objectValue;
    private String stringValue;

    public LRAResponseVo() {
    }

    public LRAResponseVo(String messageCode, String messageDescription, long messageId) {
        this.messageCode = messageCode;
        this.messageDescription = messageDescription;
        this.messageId = messageId;
    }

    public LRAResponseVo(long numberValue) {
        this.numberValue = numberValue;
    }

    public LRAResponseVo(String stringValue) {
        this.stringValue = stringValue;
    }

    public LRAResponseVo(Object objectValue) {
        this.objectValue = objectValue;
    }

    private void setSuccess() {
        this.messageCode = "LRA-0000";
        this.messageId = 0l;
        this.messageDescription = "Successful";
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(long numberValue) {
        this.numberValue = numberValue;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return "LRAResponseVo{" +
                "messageCode='" + messageCode + '\'' +
                ", messageDescription='" + messageDescription + '\'' +
                ", messageId=" + messageId +
                ", numberValue=" + numberValue +
                ", objectValue=" + objectValue +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }

}
