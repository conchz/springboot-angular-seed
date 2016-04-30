package com.github.lavenderx.http;

/**
 * Created by lavenderx on 2016-04-30.
 */
public class ErrorResponse extends BaseResponse {

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        super.setMessage(message);
    }
}
