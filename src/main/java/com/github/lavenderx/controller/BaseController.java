package com.github.lavenderx.controller;

import com.github.lavenderx.exception.PageNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lavenderx on 2016-04-28.
 */
@ControllerAdvice
public abstract class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(PageNotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected ResponseEntity<ModelAndView> pageNotFoundException() {
        return new ResponseEntity<>(new ModelAndView("404"), HttpStatus.NOT_FOUND);
    }
}
