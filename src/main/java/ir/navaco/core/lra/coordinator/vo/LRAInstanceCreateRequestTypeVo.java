package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAInstanceCreateRequestTypeVo implements Serializable {

    private Integer timeout;
    private Integer retryLimit;

    public LRAInstanceCreateRequestTypeVo() {
    }

    public LRAInstanceCreateRequestTypeVo(Integer timeout, Integer retryLimit) {
        this.timeout = timeout;
        this.retryLimit = retryLimit;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(Integer retryLimit) {
        this.retryLimit = retryLimit;
    }

    @Override
    public String toString() {
        return "LRAInstanceCreateRequestTypeVo{" +
                "timeout=" + timeout +
                ", retryLimit=" + retryLimit +
                '}';
    }
}
