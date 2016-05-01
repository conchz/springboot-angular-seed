package com.github.lavenderx.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lavenderx on 2016-05-01.
 */
@RestController
public class ExceptionHandlingController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(path = "/notFound")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView notFound() {
        return new ModelAndView("forward:/static/views/404.html");
    }

    @RequestMapping(path = ERROR_PATH)
    @ResponseStatus
    public ModelAndView error() {
        return new ModelAndView("forward:/static/views/500.html");
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
