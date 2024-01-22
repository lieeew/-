package com.leikooo.ticket.dynamicproxy.jdk;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
public class Subject implements SubjectInterface {
    @Override
    public void coreMethod() {
        System.out.println("进行业务处理");
    }
}
