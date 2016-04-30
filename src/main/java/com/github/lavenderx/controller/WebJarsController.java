package com.github.lavenderx.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lavenderx on 2016-04-28.
 */
@RestController
@Scope("prototype")
public class WebJarsController {

    private final WebJarAssetLocator assetsLocator = new WebJarAssetLocator();

    @RequestMapping("/lib/{webjar}/**")
    public ResponseEntity<Object> locateWebjarAsset(
            @PathVariable String webjar,
            HttpServletRequest request) {
        try {
            String mvcPrefix = "/lib/" + webjar + "/";
            String mvcPath = request
                    .getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
            String fullPath = assetsLocator
                    .getFullPath(webjar, mvcPath.substring(mvcPrefix.length()));

            return new ResponseEntity<>(new ClassPathResource(fullPath), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
