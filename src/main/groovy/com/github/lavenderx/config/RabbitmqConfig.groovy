package com.github.lavenderx.config

import com.github.lavenderx.annotation.AmqpRpcService
import com.google.common.base.CaseFormat
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.reflections.Reflections
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean
import org.springframework.beans.BeansException
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

/**
 * Created by lavenderx on 2016-05-05.
 */
@ComponentScan('com.github.lavenderx.mq')
@Configuration
@Slf4j
@TypeChecked
class RabbitmqConfig implements BeanFactoryPostProcessor {

    static final String SCAN_PACKAGE = 'com.github.lavenderx.common'

    String host, vhost, exchange, username, password, routingKey, queue
    int port
    long replyTimeout

    RabbitmqConfig() {
        final String rabbitmqConfigFile = System.getProperty('spring.profiles.active') + '.properties'

        try {
            InputStream inputStream = this.class.getClassLoader().getResourceAsStream(rabbitmqConfigFile)
            inputStream.withCloseable {
                Properties props = new Properties()
                props.load(inputStream)

                this.host = props.getProperty('amqp.host')
                this.port = Integer.parseInt(props.getProperty('amqp.port'))
                this.vhost = props.getProperty('amqp.vhost')
                this.exchange = props.getProperty('amqp.exchange')
                this.username = props.getProperty('amqp.username')
                this.password = props.getProperty('amqp.password')
                this.routingKey = props.getProperty('amqp.routingKey')
                this.queue = props.getProperty('amqp.queue')
                this.replyTimeout = Long.valueOf(props.getProperty('amqp.reply.timeout'))
            }
        } catch (IOException ex) {
            throw new RuntimeException('Failed to read AMQP config file')
        }
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory())
        template.setExchange(exchange)
        template.setReplyTimeout(replyTimeout)

        template
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * </p>
     * FanoutExchange: 将消息分发到所有的绑定队列, 无routingkey的概念
     * HeadersExchange: 通过添加属性key-value匹配
     * DirectExchange: 按照routingkey分发到指定队列
     * TopicExchange: 多关键字匹配
     */
    @Bean
    DirectExchange exchange() {
        new DirectExchange(exchange)
    }

    @Bean
    Queue queue() {
        new Queue(queue, true)    // 队列持久

    }

    @Bean
    Binding binding() {
        BindingBuilder.bind(queue()).to(exchange()).with(routingKey)
    }

    @Bean
    RabbitAdmin rabbitAdmin() {
        new RabbitAdmin(connectionFactory())
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port)
        connectionFactory.setUsername(username)
        connectionFactory.setPassword(password)
        connectionFactory.setVirtualHost(vhost)
        connectionFactory.setPublisherConfirms(true)

        connectionFactory
    }

    @Override
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        try {
            log.info('Initialize RabbitMQ host={}, vhost={}, exchange={}', host, vhost, exchange)
            log.info('Initialize RabbitMQ RPC client stubs')

            for (Class<?> clazz :
                    new Reflections(SCAN_PACKAGE).getTypesAnnotatedWith(AmqpRpcService.class)) {
                final String rpcServiceName = clazz.getSimpleName()
                log.info('Processing amqp rpc service annotation {}', rpcServiceName)

                // Register rpc stub
                final String rpcClientStub = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                        clazz.getSimpleName())

                log.info('Registering rpc stub {}', rpcClientStub)
                AmqpProxyFactoryBean rpcClient = createAmqpProxyFactoryBean(clazz)
                beanFactory.registerSingleton(rpcClientStub, clazz.cast(rpcClient.getObject()))
                beanFactory.initializeBean(clazz.cast(rpcClient.getObject()), rpcClientStub)

                log.info('Complete registering rpc service {}', rpcServiceName)
            }

            log.info('Complete initializing RabbitMQ')
        } catch (Exception ex) {
            throw new BeanInitializationException('Unable to configure AMQP Bean')
        }
    }

    private AmqpProxyFactoryBean createAmqpProxyFactoryBean(Class<?> serviceInterface) {
        AmqpProxyFactoryBean amqpProxyFactoryBean = new AmqpProxyFactoryBean()
        amqpProxyFactoryBean.setRoutingKey(serviceInterface.getName())
        amqpProxyFactoryBean.setServiceInterface(serviceInterface)
        amqpProxyFactoryBean.setAmqpTemplate(rabbitTemplate())
        amqpProxyFactoryBean.afterPropertiesSet()

        amqpProxyFactoryBean
    }
}
