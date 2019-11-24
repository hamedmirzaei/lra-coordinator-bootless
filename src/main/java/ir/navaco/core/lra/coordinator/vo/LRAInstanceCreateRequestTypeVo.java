package ir.navaco.core.lra.coordinator.vo;

import java.io.Serializable;

public class LRAInstanceCreateRequestTypeVo implements Serializable {
    private Integer retryLimit;

    public LRAInstanceCreateRequestTypeVo() {
    }

    public LRAInstanceCreateRequestTypeVo(Integer retryLimit) {
        this.retryLimit = retryLimit;
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
                ", retryLimit=" + retryLimit +
                '}';
    }
}
