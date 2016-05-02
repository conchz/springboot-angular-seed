package com.github.lavenderx.controller

import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.HandlerMapping
import org.webjars.WebJarAssetLocator

import javax.servlet.http.HttpServletRequest

/**
 * Created by lavenderx on 2016-04-28.
 */
@RestController
class WebJarsController {

    private static String webJarMvcPrefix = '/lib/%s/'

    private final WebJarAssetLocator assetsLocator = new WebJarAssetLocator()

    @RequestMapping(path = '/lib/{webjar}/**')
    public ResponseEntity<Object> locateWebjarAssets(
            @PathVariable String webjar,
            HttpServletRequest request) {
        try {
            String mvcPath = request
                    .getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE).toString()
            String fullPath = assetsLocator.getFullPathExact(
                    webjar,
                    mvcPath.substring(String.format(webJarMvcPrefix, webjar).length())
            )

            new ResponseEntity<>(new ClassPathResource(fullPath), HttpStatus.OK)
        } catch (Exception ex) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
    }
}
