package com.leikooo.mq;

import com.leikooo.constant.AmqpConstants;
import com.leikooo.pojo.Products;
import com.leikooo.repo.ProductRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.RecoveryCallback;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description
 */
@Slf4j
@Component
public class MessageConsumer {
    @Resource
    private ProductRepository productRepository;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = {AmqpConstants.DEAD_QUEUE}, ackMode = "MANUAL")
    public void consumerMes(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("Received message: {}", message);
        rabbitTemplate.receiveAndReply((ReceiveAndReplyCallback<String, String>) payload -> {
            log.info("Received message: {}", payload);
            return payload;
        });
        try {
            Products referenceById = productRepository.findByProductId(message);
            if (referenceById != null && referenceById.getSendRedBag() == 1) {
                //  nack 消息怎么办？
                rabbitTemplate.execute(channel -> {
                    channel.basicAck(deliveryTag, false);
                    return "";
                });
            } else {
                rabbitTemplate.execute(channel -> {
                    channel.basicNack(deliveryTag, false, false);
                    return "";
                });
            }
        } catch (Exception e) {
            rabbitTemplate.execute(channel -> {
                channel.basicNack(deliveryTag, false, false);
                return "";
            });
        };
    }
}
