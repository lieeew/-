package com.leikooo.pay.face;

import com.leikooo.pay.factory.PayContextFactory;
import com.leikooo.pojo.Order;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description 门面模式旨在「封装」，就像 SpringGateWay 一样
 */
@Component
public class PayFace {
    @Resource
    private PayContextFactory payContextFactory;

    public String pay(Order order, Integer payType) {
        return payContextFactory.getContext(payType).execute(order);
    }
}
