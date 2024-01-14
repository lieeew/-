package com.leikooo.ordermanagement.command;

import com.leikooo.ordermanagement.command.receiver.OrderCommandReceiver;
import com.leikooo.pojo.Order;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
@Component
public class OrderCommand implements OrderCommandInterface {
    @Resource
    private OrderCommandReceiver orderCommandReceiver;

    @Override
    public void execute(Order order) {
        this.orderCommandReceiver.action(order);
    }
}
