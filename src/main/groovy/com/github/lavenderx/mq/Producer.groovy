package com.github.lavenderx.mq

import groovy.transform.TypeChecked
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.CorrelationData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Created by lavenderx on 2016-05-05.
 */
@Component
@TypeChecked
class Producer implements RabbitTemplate.ConfirmCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class)

    private RabbitTemplate rabbitTemplate

    @Value('${amqp.exchange}')
    private String exchange

    @Value('${amqp.routingKey}')
    private String routingKey

    @Autowired
    void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate
        rabbitTemplate.setConfirmCallback(this)    // rabbitTemplate如果为单例的话, 那回调就是最后设置的内容
    }

    @Override
    void confirm(CorrelationData correlationData, boolean ack, String cause) {
        LOGGER.info('回调ID: ' + correlationData)
        if (ack) {
            LOGGER.info('消息成功消费')
        } else {
            LOGGER.info('消息消费失败: {}', cause)
        }
    }

    public void sendMessage(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString())
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationId)
    }
}
