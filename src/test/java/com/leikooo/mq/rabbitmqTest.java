package com.leikooo.mq;


import com.leikooo.constant.AmqpConstants;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description
 */
@SpringBootTest
public class rabbitmqTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void test() {
        AmqpAdmin amqpAdmin = new RabbitAdmin(rabbitTemplate);
        Queue deadlQueue = new Queue(AmqpConstants.DEAD_QUEUE, false);
        amqpAdmin.declareQueue(deadlQueue);

        TopicExchange deadExchange = new TopicExchange(AmqpConstants.DEAD_EXCHANGE, false, false);
        amqpAdmin.declareExchange(deadExchange);

        amqpAdmin.declareBinding(BindingBuilder.bind(deadlQueue).to(deadExchange).with(AmqpConstants.DEAD_ROUTING_KEY));

        Map<String, Object> arg = new HashMap<>();
        arg.put("x-dead-letter-exchange", AmqpConstants.DEAD_EXCHANGE);
        arg.put("x-dead-letter-routing-key", AmqpConstants.DEAD_ROUTING_KEY);
        Queue normalQueue = new Queue(AmqpConstants.NORMAL_QUEUE, false, false, false, arg);
        amqpAdmin.declareQueue(normalQueue);

        TopicExchange normalExchange = new TopicExchange(AmqpConstants.NORMAL_EXCHANGE, false, false);
        amqpAdmin.declareExchange(normalExchange);

        amqpAdmin.declareBinding(BindingBuilder.bind(normalQueue).to(normalExchange).with(AmqpConstants.NORMAL_ROUTING_KEY));
    }

    @Test
    void sendMessage() {
        rabbitTemplate.convertAndSend(AmqpConstants.DEAD_EXCHANGE, AmqpConstants.DEAD_ROUTING_KEY, "测试");
    }

}
