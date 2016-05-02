package com.github.lavenderx.controller

import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * Created by lavenderx on 2016-05-01.
 */
@RestController
class ErrorHandleController implements ErrorController {

    private static final String ERROR_PATH = '/error'

    @RequestMapping(path = '/error')
    @ResponseBody
    @ResponseStatus
    public ModelAndView error() {
        new ModelAndView('forward:/static/views/500.html')
    }

    @Override
    String getErrorPath() {
        ERROR_PATH
    }
}
