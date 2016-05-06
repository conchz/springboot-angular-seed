package com.github.lavenderx.controller

import groovy.transform.TypeChecked
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

/**
 * Created by lavenderx on 2016-05-05.
 */
@RestController
@TypeChecked
class ErrorHandleController implements ErrorController {

    @RequestMapping(path = '/error')
    @ResponseBody
    @ResponseStatus
    ModelAndView error() {
        new ModelAndView('forward:/static/views/500.html')
    }

    @Override
    String getErrorPath() {
        '/error'
    }
}
