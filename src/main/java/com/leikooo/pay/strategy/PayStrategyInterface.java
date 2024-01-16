package com.leikooo.pay.strategy;

import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public interface PayStrategyInterface {
    String pay(Order order);
}
