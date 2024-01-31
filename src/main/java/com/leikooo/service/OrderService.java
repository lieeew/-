package com.leikooo.service;

import com.google.common.collect.ImmutableMap;
import com.leikooo.audit.*;
import com.leikooo.ordermanagement.command.OrderCommand;
import com.leikooo.ordermanagement.command.invoke.OrderCommandInvoker;
import com.leikooo.ordermanagement.state.OrderState;
import com.leikooo.ordermanagement.state.OrderStateChangeAction;
import com.leikooo.pay.face.PayFace;
import com.leikooo.pojo.Order;
import com.leikooo.constant.StateMachineConstant;
import com.leikooo.service.inner.OrderServiceInterface;
import com.leikooo.transaction.colleague.AbstractCustomer;
import com.leikooo.transaction.colleague.Buyer;
import com.leikooo.transaction.colleague.Payer;
import com.leikooo.transaction.mediator.Mediator;
import com.leikooo.util.RedisCommonProcessor;
import jakarta.annotation.Resource;
import jakarta.persistence.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/12
 * @description
 */
@Service
@Slf4j
public class OrderService implements OrderServiceInterface {
    @Resource
    private RedisCommonProcessor redisCommonProcessor;

    @Resource
    private StateMachine<OrderState, OrderStateChangeAction> stateMachine;

    @Resource
    private StateMachinePersister<OrderState, OrderStateChangeAction, String> stateMachinePersister;

    @Resource
    private OrderCommand orderCommand;

    @Resource
    private PayFace payFace;

    @Resource
    private Mediator mediator;

    @Resource
    private CreatOrderLog creatOrderLog;

    @Resource
    private PayOrderLog payOrderLog;

    @Resource
    private ReceiveOrderLog receiveOrderLog;

    @Resource
    private SendOrderLog sendOrderLog;

    @Override
    public Order createOrder(final String productId) {
        String orderId = "OID" + productId;
        Order order = Order.builder().productId(productId).orderId(orderId).orderState(OrderState.ORDER_WAIT_PAY).build();
        redisCommonProcessor.set(orderId, order, 600);
        OrderCommandInvoker orderCommandInvoker = new OrderCommandInvoker();
        orderCommandInvoker.invoke(orderCommand, order);
        // 发到一个 queue 里面
        OrderAuditLog orderAuditLog = creatOrderLog.creatAuditLog("account", "creat", orderId);
        return order;
    }

    @Override
    public Order payOrder(final String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.PAY_ORDER).setHeader(StateMachineConstant.MESSAGE_HEADER_KEY, order).build();
        if (!changeStateAction(message, order)) {
            return null;
        }
        OrderAuditLog orderAuditLog = payOrderLog.creatAuditLog("account", "pay", orderId);
        return order;
    }

    @Override
    public Order sendOrder(final String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.SEND_ORDER).setHeader(StateMachineConstant.MESSAGE_HEADER_KEY, order).build();
        if (!changeStateAction(message, order)) {
            return null;
        }
        OrderAuditLog orderAuditLog = sendOrderLog.creatAuditLog("account", "send", orderId);
        return order;
    }

    @Override
    public Order receiveOrder(final String orderId) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message<OrderStateChangeAction> message = MessageBuilder.withPayload(OrderStateChangeAction.RECEIVE_ORDER).setHeader(StateMachineConstant.MESSAGE_HEADER_KEY, order).build();
        if (!changeStateAction(message, order)) {
            return null;
        }
        OrderAuditLog orderAuditLog = receiveOrderLog.creatAuditLog("account", "receive", orderId);
        return order;
    }

    @Override
    public String getPayUrl(String orderId, Integer payType, Float price) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        Order completeOrder = Order.builder().price(price).productId(order.getProductId()).orderState(order.getOrderState()).orderId(orderId).build();
        return payFace.pay(completeOrder, payType);
    }

    @Override
    public void friendlyPay(String sourceCustomer, String orderId, String targetCustomer, String payResult, String role) {
        Payer payer = new Payer(mediator, orderId, sourceCustomer);
        Buyer buyer = new Buyer(mediator, orderId, targetCustomer);
        Mediator.customerInstances.put(orderId, ImmutableMap.of("payer", payer, "buyer", buyer));
        if ("B".equals(role)) {
            buyer.messageTransfer(orderId, targetCustomer, payResult);
        } else if ("P".equals(role)) {
            payer.messageTransfer(orderId, sourceCustomer, payResult);
        }
    }

    /**
     * 修改 stateMachine 的订单状态
     *
     * @param message
     * @param order
     * @return
     */
    private boolean changeStateAction(Message<OrderStateChangeAction> message, Order order) {
        try {
            stateMachine.start();
            stateMachinePersister.restore(stateMachine, order.getOrderId() + StateMachineConstant.STATEMACHINE_KEY_SUFFIX);
            boolean result = stateMachine.sendEvent(message);
            // 存储状态机到 redis
            stateMachinePersister.persist(stateMachine, order.getOrderId() + StateMachineConstant.STATEMACHINE_KEY_SUFFIX);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            stateMachine.stop();
        }
        return false;
    }
}
