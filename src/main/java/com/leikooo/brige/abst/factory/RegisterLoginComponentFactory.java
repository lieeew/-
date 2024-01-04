package com.leikooo.brige.abst.factory;

import com.leikooo.brige.abst.AbstractRegisterLoginComponent;
import com.leikooo.brige.abst.RegisterLoginComponent;
import com.leikooo.brige.function.RegisterLoginFuncInterface;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/4
 * @description
 */
public class RegisterLoginComponentFactory {
    // 缓存一下，这样就会不频繁的造对象
    public static Map<String, AbstractRegisterLoginComponent> componentMap = new ConcurrentHashMap<>();
    public static Map<String, RegisterLoginFuncInterface> funcMap = new ConcurrentHashMap<>();

    public static AbstractRegisterLoginComponent getRegisterLoginComponent(String type) {
        AbstractRegisterLoginComponent component = componentMap.get(type);
        if (component != null) {
            return component;
        }
        synchronized (componentMap) {
            // 需要加一个双重校验
            if (component == null) {
                component = new RegisterLoginComponent(funcMap.get(type));
                componentMap.put(type, component);
            }
        }
        return component;
    }
}
