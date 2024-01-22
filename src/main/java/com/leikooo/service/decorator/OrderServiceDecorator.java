package com.leikooo.service.decorator;

import com.leikooo.constant.AmqpConstants;
import com.leikooo.pojo.Order;
import com.leikooo.pojo.Products;
import com.leikooo.repo.ProductRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description 这里只是完成 updateScoreAndSendRedPaper 是不够的，我们需要的是完整的逻辑，而不只是积分、红包逻辑
 */
@Slf4j
@Service
public class OrderServiceDecorator extends AbstractOrderServiceDecorator {
    @Resource
    private ProductRepository productRepository;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${daley.service.time}")
    private String delayTime;

    /**
     *
     *
     * @param productId
     * @param serviceLevel 0 正常服务；1 延迟服务； 2 暂停服务
     * @param price
     */
    @Override
    public void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price) {
        switch (serviceLevel) {
            case 0 -> {
                int score = Math.round(price) / 100;
                Products byProductId = productRepository.findByProductId(productId);
                log.info("正常处理，为用户增加积分 " + score);
                if (byProductId != null && byProductId.getSendRedBag() == 1) {
                    log.info("正常处理，为用户发送红包！");
                }
            }
            case 1 -> {
                log.info("延迟处理，为用户增加积分");
                MessageProperties messageProperties = new MessageProperties();
                messageProperties.setExpiration(delayTime);
                Message message = new Message(productId.getBytes(), messageProperties);
                rabbitTemplate.send(AmqpConstants.NORMAL_EXCHANGE, AmqpConstants.NORMAL_ROUTING_KEY, message);
            }
            case 3 -> log.info("暂停服务");
            default -> throw new UnsupportedOperationException("不支持的服务");
        }
    }

    public Order decoratorPay(String orderId, int serverLevel, float price){
        Order order = super.payOrder(orderId);
        // 不会影响支付主流程
        try {
            this.updateScoreAndSendRedPaper(order.getProductId(), serverLevel, price);
        } catch (Exception e) {
            // 重试机制，不能影响支付主流程1
            log.error("更新积分和发送红包失败", e);
        }
        return order;
    }
}
