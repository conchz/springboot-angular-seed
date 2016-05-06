package com.github.lavenderx.controller

import com.github.lavenderx.http.BaseResponse
import com.github.lavenderx.http.ErrorResponse
import groovy.transform.TypeChecked
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Created by lavenderx on 2016-05-05.
 */
@ControllerAdvice
@TypeChecked
abstract class BaseController {

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true))
    }

    @ResponseBody
    protected ResponseEntity<? super BaseResponse> apply(BaseResponse response) {
        new ResponseEntity<>(response, HttpStatus.OK)
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<? super BaseResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                ex.getMessage()
        )

        new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
