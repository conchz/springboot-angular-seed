package com.github.lavenderx.http;

/**
 * Created by lavenderx on 2016-05-05.
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {

    }

    public ErrorResponse(String status, String message) {
        super.setStatus(status);
        super.setMessage(message);
    }
}
