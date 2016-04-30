package com.github.lavenderx.controller;

import com.github.lavenderx.exception.NoDataException;
import com.github.lavenderx.http.BaseResponse;
import com.github.lavenderx.http.ErrorResponse;
import com.github.lavenderx.http.ResponseCodes;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lavenderx on 2016-04-28.
 */
@ControllerAdvice
public abstract class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(NoDataException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    protected ResponseEntity<? super BaseResponse> noDataException() {
        return new ResponseEntity<>(new ErrorResponse(ResponseCodes.NO_DATA), HttpStatus.NO_CONTENT);
    }
}
