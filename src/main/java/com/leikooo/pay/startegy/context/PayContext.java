package com.leikooo.pay.startegy.context;

import com.leikooo.pay.factory.AbstractPayContext;
import com.leikooo.pay.startegy.PayStrategyInterface;
import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public class PayContext extends AbstractPayContext {
    // 关联策略类
    private final PayStrategyInterface payStrategy;

    public PayContext(PayStrategyInterface payStrategy) {
        this.payStrategy = payStrategy;
    }

    @Override
    public String execute(Order order){
        return this.payStrategy.pay(order);
    }
}
