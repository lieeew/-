package com.leikooo.pay.factory;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public enum StrategyEnum {
    alipay("com.leikooo.pay.strategy.AlipayStrategy"),
    wechatPay("com.leikooo.pay.strategy.WeChatPayStrategy");

    String value = "";

    StrategyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
