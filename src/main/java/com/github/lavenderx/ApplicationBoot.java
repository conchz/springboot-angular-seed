package com.github.lavenderx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

/**
 * <p>
 * <a href="https://justinrodenbostel.com/2014/05/30/part-5-integrating-spring-security-with-spring-boot-web/"></a>
 * <a href="http://info.michael-simons.eu/2014/04/15/spring-boot-as-a-backend-for-angularjs/"></a>
 * <a href="http://blog.csdn.net/catoop/article/details/50501706"></a>
 * </p>
 * Created by lavenderx on 2016-05-02.
 */
@ComponentScan("com.github.lavenderx.config")
@SpringBootApplication
public class ApplicationBoot {

    private static final String PROFILE = "spring.profiles.active";

    public static void main(String[] args) {
        if (StringUtils.isEmpty(System.getProperty(PROFILE))) {
            System.setProperty(PROFILE, "prod");
        }

        SpringApplication.run(ApplicationBoot.class, args);
    }
}
