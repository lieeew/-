package com.leikooo.pay.face;

import com.leikooo.pay.startegy.AlipayStrategy;
import com.leikooo.pay.startegy.WechatPayStrategy;
import com.leikooo.pay.startegy.context.PayContext;
import com.leikooo.pojo.Order;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description 门面模式旨在「封装」，就像 SpringGateWay 一样
 */
@Component
public class PayFace {
    public String pay(Order order, Integer payType) {
        return switch (payType) {
            case 1 -> new PayContext(new AlipayStrategy()).execute(order);
            case 2 -> new PayContext(new WechatPayStrategy()).execute(order);
            default -> throw new UnsupportedOperationException("不支持 " + payType + " 支付方式");
        };
    }
}
