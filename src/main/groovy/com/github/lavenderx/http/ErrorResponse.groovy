package com.github.lavenderx.http

import groovy.transform.TypeChecked

/**
 * Created by lavenderx on 2016-05-05.
 */
@TypeChecked
class ErrorResponse extends BaseResponse {

    ErrorResponse() {

    }

    ErrorResponse(String status, String message) {
        super.setStatus(status)
        super.setMessage(message)
    }
}
