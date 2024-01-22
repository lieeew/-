package com.leikooo.ticket.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
public class JdkProxyTest {
    public static void main(String[] args) {
        // 被代理的对象
        Subject subject = new Subject();
        // 增强类
        Advice advice = new Advice();
        SubjectInterface proxyInstance = (SubjectInterface) Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                advice.before();
                Object invoke = method.invoke(subject, args);
                advice.after();
                return invoke;
            }
        });
        proxyInstance.coreMethod();
    }
}
