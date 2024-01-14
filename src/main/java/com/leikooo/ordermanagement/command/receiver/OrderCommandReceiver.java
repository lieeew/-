package com.leikooo.ordermanagement.command.receiver;

import com.leikooo.ordermanagement.state.OrderState;
import com.leikooo.pojo.Order;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description 真正执行命令的接收者，核心代码在这里
 */
@Component
public class OrderCommandReceiver {

    public void action(Order order) throws UnsupportedOperationException {
        assert order != null : "order 不符合规范";
        OrderState orderState = order.getOrderState();
        switch (orderState) {
            case ORDER_WAIT_PAY -> {
                System.out.println("创建订单");
                System.out.println("存入到 DB");
            }
            case ORDER_WAIT_SEND -> {
                System.out.println("订单 id " + order.getOrderId());
                System.out.println("存入 DB");
                System.out.println("通过 queue 通知财务部门");
                System.out.println("通过 queue 通知发货部门");
            }
            case ORDER_WAIT_RECEIVE -> {
                System.out.println("订单 id " + order.getOrderId());
                System.out.println("存入 DB");
            }
            case ORDER_FINISH -> {
                System.out.println("订单 id " + order.getOrderId() + "已完成");
            }
            default -> throw new UnsupportedOperationException(String.format("order state is %s error!", orderState));
        }
    }
}