package com.github.lavenderx.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by lavenderx on 2016-05-02.
 */
@Order(1)
@Component
public class SimpleStartupRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStartupRunner.class);

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>> Spring Boot extra startup is running <<<<<<<<<<<<<<<<");
    }
}
