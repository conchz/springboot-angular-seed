package com.github.lavenderx.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by lavenderx on 2016-04-30.
 */
@Configuration
public class BeanInitializer {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/views/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/views/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/views/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        };
    }
}
