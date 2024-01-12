package com.leikooo.ordermanagement.state;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/9
 * @description
 */
public enum OrderState {
    ORDER_WAIT_PAY, // 待支付
    ORDER_WAIT_SEND, // 待发货
    ORDER_WAIT_RECEIVE, // 待收货
    ORDER_FINISH; // 完成订单
}
