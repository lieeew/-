package com.leikooo.ticket.dynamicproxy.jdk;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description 增强的对象
 */
public class Advice {
    public  void before() {
        System.out.println("前置处理方法！");
    }
    public void after() {
        System.out.println("后置增强方法！");
    }
}
