package com.github.lavenderx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lavenderx on 2016-04-30.
 */
@Configuration
@Import(RootConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        HandlerExceptionResolver pageNotFoundExceptionResolver =
                (request, response, handler, ex) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("ex", ex);
                    String viewName = ClassUtils.getShortName(ex.getClass());

                    return new ModelAndView(viewName, model);
                };

        exceptionResolvers.add(pageNotFoundExceptionResolver);
    }
}
