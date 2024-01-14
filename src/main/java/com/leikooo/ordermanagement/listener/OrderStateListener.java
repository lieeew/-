package com.leikooo.ordermanagement.listener;

import com.alipay.api.domain.UpdatedAuthenticationDetails;
import com.leikooo.ordermanagement.command.OrderCommand;
import com.leikooo.ordermanagement.command.invoke.OrderCommandInvoker;
import com.leikooo.ordermanagement.state.OrderState;
import com.leikooo.ordermanagement.state.OrderStateChangeAction;
import com.leikooo.pojo.Order;
import com.leikooo.constant.StateMachineConstant;
import com.leikooo.util.RedisCommonProcessor;
import jakarta.annotation.Resource;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/9
 * @description
 */
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {
    @Resource
    private RedisCommonProcessor redisCommonProcessor;

    @Resource
    private OrderCommand orderCommand;

    @OnStateChanged(source = "ORDER_WAIT_PAY", target = "ORDER_WAIT_SEND")
    public boolean payToSend(Message<OrderStateChangeAction> message) {
        final Order order = (Order) message.getHeaders().get(StateMachineConstant.MESSAGE_HEADER_KEY);
        assert order != null : "order 不存在";
        if (order.getOrderState() != OrderState.ORDER_WAIT_PAY) {
            throw new UnsupportedOperationException("order state error !");
        }
        final Order updateOrder = Order.builder().orderId(order.getOrderId()).orderState(OrderState.ORDER_WAIT_SEND).price(order.getPrice()).productId(order.getProductId()).build();
        redisCommonProcessor.set(order.getOrderId(), updateOrder, 900);
        OrderCommandInvoker orderCommandInvoker = new OrderCommandInvoker();
        orderCommandInvoker.invoke(orderCommand, updateOrder);
        return true;
    }

    @OnStateChanged(source = "ORDER_WAIT_SEND", target = "ORDER_WAIT_RECEIVE")
    public boolean sendToReceive(Message<OrderStateChangeAction> message) {
        final Order order = (Order) message.getHeaders().get(StateMachineConstant.MESSAGE_HEADER_KEY);
        assert order != null : "order 不存在";
        if (order.getOrderState() != OrderState.ORDER_WAIT_SEND) {
            throw new UnsupportedOperationException("order state error !");
        }
        final Order updateOrder = Order.builder().orderId(order.getOrderId()).orderState(OrderState.ORDER_WAIT_RECEIVE).price(order.getPrice()).productId(order.getProductId()).build();
        redisCommonProcessor.set(order.getOrderId(), updateOrder, 900);
        OrderCommandInvoker orderCommandInvoker = new OrderCommandInvoker();
        orderCommandInvoker.invoke(orderCommand, updateOrder);
        return true;
    }

    @OnStateChanged(source = "ORDER_WAIT_RECEIVE", target = "ORDER_FINISH")
    public boolean receiveToFinish(Message<OrderStateChangeAction> message) {
        final Order order = (Order) message.getHeaders().get(StateMachineConstant.MESSAGE_HEADER_KEY);
        assert order != null : "order 不存在";
        if (order.getOrderState() != OrderState.ORDER_WAIT_RECEIVE) {
            throw new UnsupportedOperationException("order state error !");
        }
        final Order updateOrder = Order.builder().orderId(order.getOrderId()).orderState(OrderState.ORDER_FINISH).price(order.getPrice()).productId(order.getProductId()).build();
        redisCommonProcessor.set(order.getOrderId(), updateOrder, 600);
        redisCommonProcessor.remove(order.getOrderId() + StateMachineConstant.STATEMACHINE_KEY_SUFFIX);
        OrderCommandInvoker orderCommandInvoker = new OrderCommandInvoker();
        orderCommandInvoker.invoke(orderCommand, updateOrder);
        return true;
    }
}
