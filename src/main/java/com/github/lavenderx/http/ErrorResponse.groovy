package com.github.lavenderx.http

/**
 * Created by lavenderx on 2016-04-30.
 */
class ErrorResponse extends BaseResponse {

    ErrorResponse() {
    }

    ErrorResponse(String status, String message) {
        super.setStatus(status)
        super.setMessage(message)
    }
}
