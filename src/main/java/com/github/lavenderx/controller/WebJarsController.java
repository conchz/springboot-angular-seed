package com.github.lavenderx.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lavenderx on 2016-05-05.
 */
@RestController
public class WebJarsController {

    private static String webJarMvcPrefix = "/lib/%s/";

    private final WebJarAssetLocator assetsLocator = new WebJarAssetLocator();

    /**
     * Request for loading webjar.
     *
     * @param webjar  webjar's name
     * @param request {@link HttpServletRequest}
     * @return webjar resource
     */
    @RequestMapping(path = "/lib/{webjar}/**", method = RequestMethod.GET)
    public ResponseEntity<Object> locateWebjarAssets(
            @PathVariable String webjar,
            HttpServletRequest request) {
        try {
            String mvcPath = request
                    .getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString();
            String fullPath = assetsLocator.getFullPathExact(
                    webjar,
                    mvcPath.substring(String.format(webJarMvcPrefix, webjar).length())
            );

            return new ResponseEntity<>(new ClassPathResource(fullPath), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
