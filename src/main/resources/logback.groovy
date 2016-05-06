/**
 * Doc: <a href="http://logback.qos.ch/manual/configuration.html"></a>
 * <a href="http://logback.qos.ch/translator/asGroovy.html"></a>
 * <a href="http://logback.qos.ch/manual/groovy.html">Groovy configuration</a>
 * This config file will be automatically reloaded every minute
 */

import ch.qos.logback.classic.AsyncAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.INFO

def APP_LOG_HOME = System.getProperty("user.home") + "/logs/springboot-angular-seed"

scan("60 seconds")
appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        pattern = "%d [%thread] %-5level %logger{15} - %message%n%xException{10}"
    }
}
appender("ROLLING", RollingFileAppender) {
    file = "${APP_LOG_HOME}/application.log"
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName("UTF-8")
        Pattern = "%date [%level] from %logger in %thread - %message%n%xException"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "${APP_LOG_HOME}/application-%d{yyyy-MM-dd}.log"
    }
}
appender("ASYNCFILE", AsyncAppender) {
    appenderRef("ROLLING")
}
appender("ASYNCSTDOUT", AsyncAppender) {
    appenderRef("STDOUT")
}

root(INFO, ["ASYNCFILE", "ASYNCSTDOUT"])
