package com.github.lavenderx.config;

import com.github.lavenderx.annotation.AmqpRpcService;
import com.google.common.base.CaseFormat;
import com.rabbitmq.client.Channel;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <a href="http://www.ithao123.cn/content-10535193.html"></a>
 * </p>
 * Created by lavenderx on 2016-05-03.
 */
@Configuration
public class RabbitmqConfig implements BeanFactoryPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqConfig.class);
    private static final String SCAN_PACKAGE = "com.github.lavenderx.common";

    private static final String DEFAULT_EXCHANGE = "spring-boot-exchange";
    private static final String DEFAULT_ROUTINGKEY = "spring-boot-routingKey";
    private static final String DEFAULT_QUEUE = "spring-boot-queue";

    private final String host;

    private final int port;

    private final String vhost;

    private final String exchange;

    private final String username;

    private final String password;

    private final long replyTimeout;

    public RabbitmqConfig() {
        final String rabbitmqConfigFile =
                System.getProperty("spring.profiles.active") + ".properties";

        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(rabbitmqConfigFile)) {
            Properties props = new Properties();
            props.load(inputStream);

            this.host = props.getProperty("amqp.host");
            this.port = Integer.parseInt(props.getProperty("amqp.port"));
            this.vhost = props.getProperty("amqp.vhost");
            this.exchange = props.getProperty("amqp.exchange");
            this.username = props.getProperty("amqp.username");
            this.password = props.getProperty("amqp.password");
            this.replyTimeout = Long.valueOf(props.getProperty("amqp.reply.timeout"));
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read AMQP config file");
        }
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setEncoding("UTF-8");
        template.setExchange(exchange);
        template.setReplyTimeout(replyTimeout);

        return template;
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
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue() {
        return new Queue(DEFAULT_QUEUE, true);    // 队列持久

    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(DEFAULT_ROUTINGKEY);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPublisherConfirms(true);

        return connectionFactory;
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        SimpleMessageListenerContainer container =
//                new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(16);
//        container.setConcurrentConsumers(4);
//        container.setMessageListener(new AbstractAdaptableMessageListener() {
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                LOGGER.info("Received message: {}", new String(body));
//                // 确认消息成功消费
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//
//        return container;
//    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        try {
            LOGGER.info("Initialize RabbitMQ host={}, vhost={}, exchange={}", host, vhost, exchange);
            LOGGER.info("Initialize RabbitMQ RPC client stubs");

            for (Class<?> clazz :
                    new Reflections(SCAN_PACKAGE).getTypesAnnotatedWith(AmqpRpcService.class)) {
                final String rpcServiceName = clazz.getSimpleName();
                LOGGER.info("Processing amqp rpc service annotation {}", rpcServiceName);

                // Register rpc stub
                final String rpcClientStub = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
                        clazz.getSimpleName());

                LOGGER.info("Registering rpc stub {}", rpcClientStub);
                AmqpProxyFactoryBean rpcClient = createAmqpProxyFactoryBean(clazz);
                beanFactory.registerSingleton(rpcClientStub, clazz.cast(rpcClient.getObject()));
                beanFactory.initializeBean(clazz.cast(rpcClient.getObject()), rpcClientStub);

                LOGGER.info("Complete registering rpc service {}", rpcServiceName);
            }

            LOGGER.info("Complete initializing RabbitMQ");
        } catch (Exception ex) {
            throw new BeanInitializationException("Unable to configure AMQP Bean");
        }
    }

    private AmqpProxyFactoryBean createAmqpProxyFactoryBean(Class<?> serviceInterface) {
        AmqpProxyFactoryBean amqpProxyFactoryBean = new AmqpProxyFactoryBean();
        amqpProxyFactoryBean.setRoutingKey(serviceInterface.getName());
        amqpProxyFactoryBean.setServiceInterface(serviceInterface);
        amqpProxyFactoryBean.setAmqpTemplate(rabbitTemplate());
        amqpProxyFactoryBean.afterPropertiesSet();

        return amqpProxyFactoryBean;
    }
}
