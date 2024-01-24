package com.leikooo.config;

import com.leikooo.constant.AmqpConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a
 */
@Slf4j
@Configuration
public class RabbitMQConfig {
    private final CachingConnectionFactory cachingConnectionFactory;

    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
        this.cachingConnectionFactory.setPublisherReturns(true);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    public Queue createNormalQueue() {

        return QueueBuilder.durable(AmqpConstants.NORMAL_QUEUE)
                .withArgument("x-dead-letter-exchange", AmqpConstants.DEAD_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AmqpConstants.DEAD_ROUTING_KEY)
                .build();
    }

    @Bean
    public Declarables createDeadLetterSchema() {
        return new Declarables(
                new Queue(AmqpConstants.DEAD_QUEUE),
                new DirectExchange(AmqpConstants.NORMAL_EXCHANGE),
                new DirectExchange(AmqpConstants.DEAD_EXCHANGE),
                new Binding(AmqpConstants.NORMAL_QUEUE, Binding.DestinationType.QUEUE, AmqpConstants.NORMAL_EXCHANGE, AmqpConstants.NORMAL_ROUTING_KEY, null),
                new Binding(AmqpConstants.DEAD_QUEUE, Binding.DestinationType.QUEUE, AmqpConstants.DEAD_EXCHANGE, AmqpConstants.DEAD_ROUTING_KEY, null)
        );
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(converter);
        template.setConfirmCallback((correlationData, ack, cause) -> log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        return template;
    }

}