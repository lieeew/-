package com.leikooo.duty.builder;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/16
 * @description
 */
public enum HandlerEnum {
    city("com.leikooo.duty.CityHandler"),
    sex("com.leikooo.duty.SexHandler"),
    product("com.leikooo.duty.ProductHandler");

    String value = "";

    HandlerEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
