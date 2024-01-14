package com.leikooo.ordermanagement.command.invoke;

import com.leikooo.ordermanagement.command.OrderCommandInterface;
import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public class OrderCommandInvoker {
    public void invoke(OrderCommandInterface orderCommand, Order order) {
        orderCommand.execute(order);
    }
}
