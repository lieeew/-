package com.leikooo.mq;


import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

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
        Queue normalQueue = new Queue("normalQueue", false);
        amqpAdmin.declareQueue(normalQueue);

        TopicExchange normalExchange = new TopicExchange("normalExchange", false, false);
        amqpAdmin.declareExchange(normalExchange);

        amqpAdmin.declareBinding(BindingBuilder.bind(normalQueue).to(normalExchange).with("myRKey"));

        Queue deadlQueue = new Queue("deadQueue", false);
        amqpAdmin.declareQueue(deadlQueue);

        TopicExchange deadExchange = new TopicExchange("deadExchange", false, false);
        amqpAdmin.declareExchange(deadExchange);

        amqpAdmin.declareBinding(BindingBuilder.bind(deadlQueue).to(deadExchange).with("deadRKey"));
    }
}
