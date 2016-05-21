package com.github.lavenderx.http;

import java.io.Serializable;

/**
 * Created by lavenderx on 2016-05-05.
 */
public abstract class BaseResponse implements Serializable {

    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
