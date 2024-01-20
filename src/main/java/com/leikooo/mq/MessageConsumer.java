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
import java.nio.charset.StandardCharsets;

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

    @RabbitListener(queues = {AmqpConstants.DEAD_QUEUE}, ackMode = "MANUAL")
    public void consumerMes(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("Received message: {}", message);

        try {
            Products byProductId = productRepository.findByProductId(message);

            if (byProductId != null && byProductId.getSendRedBag() == 1) {
                log.info("Normal processing, sending red packet to the user!");
                channel.basicAck(deliveryTag, false);
            } else {
                log.info("Skipping processing, not sending red packet to the user.");
                // Depending on your logic, you might want to use basicReject instead of basicNack
                channel.basicNack(deliveryTag, false, false);
            }
        } catch (IOException e) {
            log.error("Error processing message: {}", e.getMessage());
            // Handle the exception appropriately, e.g., log it, throw a custom exception, etc.
        }
    }
}
