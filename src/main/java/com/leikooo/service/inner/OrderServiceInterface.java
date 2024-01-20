package com.leikooo.service.inner;

import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/20
 * @description
 */
public interface OrderServiceInterface {
    Order createOrder(String productId);
    Order payOrder(String orderId);
    Order sendOrder(String orderId);
    Order receiveOrder(String orderId);
    String getPayUrl(String orderId, Integer payType, Float price);
}
