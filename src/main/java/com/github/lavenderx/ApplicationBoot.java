package com.github.lavenderx;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="http://info.michael-simons.eu/2014/04/15/spring-boot-as-a-backend-for-angularjs/"></a>
 * <a href="http://blog.csdn.net/catoop/article/details/50501706"></a>
 * Created by lavenderx on 2016-04-28.
 */
@SpringBootApplication
public class ApplicationBoot extends WebMvcConfigurerAdapter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }

    @Override
    public void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        HandlerExceptionResolver pageNotFoundExceptionResolver =
                (request, response, handler, ex) -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("ex", ex);
                    // 这里可根据不同异常引起类做不同处理方式, 返回相应的页面.
                    String viewName = ClassUtils.getShortName(ex.getClass());

                    return new ModelAndView(viewName, model);
                };

        exceptionResolvers.add(pageNotFoundExceptionResolver);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        };
    }
}
