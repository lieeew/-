package com.leikooo.controller;

import com.leikooo.constant.AmqpConstants;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/24
 * @description
 */
@Controller
@RequestMapping("/mq")
public class MqController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/send")
    public void sendMess(@RequestParam String mes, @RequestParam String uniqueId) {
        CorrelationData correlationData = new CorrelationData(uniqueId);
        rabbitTemplate.convertAndSend(AmqpConstants.DEAD_EXCHANGE, AmqpConstants.DEAD_ROUTING_KEY, mes, correlationData);
    }
}
