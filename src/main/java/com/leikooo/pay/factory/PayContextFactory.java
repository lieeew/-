package com.leikooo.pay.factory;

import com.leikooo.pay.startegy.PayStrategyInterface;
import com.leikooo.pay.startegy.context.PayContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
@Slf4j
@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext> {
    private static final Map<String, PayContext> contexts = new ConcurrentHashMap<>();

    @Override
    public PayContext getContext(Integer payType) {
        StrategyEnum strategyEnum = payType == 1 ? StrategyEnum.alipay : payType == 2 ? StrategyEnum.wechatPay : null;
        if (strategyEnum == null) {
            throw new UnsupportedOperationException();
        }
        PayContext payContext = contexts.get(strategyEnum.name());
        if (payContext == null) {
            try {
                PayStrategyInterface payStrategyInterface = (PayStrategyInterface) Class.forName(strategyEnum.getValue()).getDeclaredConstructor().newInstance();
                PayContext context = new PayContext(payStrategyInterface);
                contexts.put(strategyEnum.name(), context);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
                     InvocationTargetException e) {
                log.error("factory 创建对象失败" + e.getMessage());
            }
        }
        return contexts.get(strategyEnum.name());
    }
}
