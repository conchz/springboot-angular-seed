package com.github.lavenderx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by lavenderx on 2016-05-05.
 */
@Order(1)
@Component
@Slf4j
public class SimpleStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info(">>>>>>>>>>>>>>>> Spring Boot extra startup is running <<<<<<<<<<<<<<<<");
    }
}
