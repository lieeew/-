package com.leikooo.mq;

import com.leikooo.constant.AmqpConstants;
import com.leikooo.pojo.Products;
import com.leikooo.repo.ProductRepository;
import com.rabbitmq.client.Channel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

    @RabbitListener(queues = {AmqpConstants.DEAD_QUEUE})
    public void consumerMes(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("Received message: {}", message);
        try {
            Products referenceById = productRepository.findByProductId(message);
            if (referenceById != null && referenceById.getSendRedBag() == 1) {
                //  nack 消息怎么办？
                channel.basicAck(deliveryTag, false);
            } else {
                channel.basicNack(deliveryTag, false, true);
            }
        } catch (Exception e) {
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
