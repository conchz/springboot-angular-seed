package com.github.lavenderx.controller

import com.github.lavenderx.http.BaseResponse
import com.github.lavenderx.http.ErrorResponse
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by lavenderx on 2016-04-28.
 */
@ControllerAdvice
abstract class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true))
    }

    @ResponseBody
    protected ResponseEntity<? super BaseResponse> apply(BaseResponse response) {
        new ResponseEntity<>(response, HttpStatus.OK)
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus
    public ResponseEntity<? super BaseResponse> handleException(Exception ex) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse(
                GString.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage()
        )

        new ResponseEntity<BaseResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
