package com.github.lavenderx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lavenderx on 2016-04-28.
 */
@RestController
public class IndexController extends BaseController {

    @RequestMapping(path = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index");
    }

}
