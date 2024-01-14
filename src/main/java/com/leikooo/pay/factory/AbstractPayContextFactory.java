package com.leikooo.pay.factory;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public abstract class AbstractPayContextFactory<T> {
    public abstract T getContext(Integer payType);
}
