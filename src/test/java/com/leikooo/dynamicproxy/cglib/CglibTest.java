package com.leikooo.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/22
 * @description
 */
public class CglibTest {
    public static void main(String[] args) {
        Subject subject = new Subject();
        Advice advice = new Advice();
        // 创建增强容器
        Enhancer enhancer = new Enhancer();
        // 设置增强目录
        enhancer.setSuperclass(subject.getClass());
        // 设置回溯
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                advice.before();
                Object invoke = method.invoke(subject, objects);
                advice.after();
                return invoke;
            }
        });
        Subject targetProxy = (Subject) enhancer.create();
        targetProxy.coreMethod();
    }
}
