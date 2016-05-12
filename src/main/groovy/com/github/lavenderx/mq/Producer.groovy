package com.github.lavenderx.mq

import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.CorrelationData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Created by lavenderx on 2016-05-05.
 */
//@Component
@Slf4j
@TypeChecked
class Producer implements RabbitTemplate.ConfirmCallback {

    @Value('${amqp.exchange}')
    String exchange

    @Value('${amqp.routingKey}')
    String routingKey

    RabbitTemplate rabbitTemplate

    @Autowired
    void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate
        rabbitTemplate.setConfirmCallback(this)    // rabbitTemplate如果为单例的话, 那回调就是最后设置的内容
    }

    @Override
    void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info('回调ID: ' + correlationData)
        if (ack) {
            log.info('消息成功消费')
        } else {
            log.info('消息消费失败: {}', cause)
        }
    }

    void sendMessage(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString())
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationId)
    }
}
