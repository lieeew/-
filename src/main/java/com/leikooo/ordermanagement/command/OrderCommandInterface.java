package com.leikooo.ordermanagement.command;

import com.leikooo.pojo.Order;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/14
 * @description
 */
public interface OrderCommandInterface {
    void execute(Order order);
}
