package com.nhnacademy;

public class InvalidStatusException extends RuntimeException {
    int code;
    String reason;

    public InvalidStatusException(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
