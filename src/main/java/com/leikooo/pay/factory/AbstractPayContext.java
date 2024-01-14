package com.leikooo.pay.factory;

import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public abstract class AbstractPayContext {
    public abstract String execute(Order order);
}
