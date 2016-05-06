package com.github.lavenderx.config

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * Created by lavenderx on 2016-05-05.
 */
@Order(1)
@Component
@Slf4j
@TypeChecked
class SimpleStartupRunner implements CommandLineRunner {

    @Override
    void run(String... strings) throws Exception {
        log.info('>>>>>>>>>>>>>>>> Spring Boot extra startup is running <<<<<<<<<<<<<<<<')
    }
}
