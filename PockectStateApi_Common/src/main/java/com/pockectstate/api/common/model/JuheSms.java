package com.pockectstate.api.common.model;

/**
 *@Author feri
 *@Date Created in 2019/7/9 15:12
 */
public class JuheSms {
    private int error_code;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
