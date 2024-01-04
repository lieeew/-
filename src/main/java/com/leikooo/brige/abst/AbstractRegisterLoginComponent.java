package com.leikooo.brige.abst;

import com.leikooo.brige.function.RegisterLoginFuncInterface;
import com.leikooo.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @description
 */
public abstract class AbstractRegisterLoginComponent {
    // 引入对应的接口, 修饰词是 protected 让子类可以调用
    protected RegisterLoginFuncInterface funcInterface;

    public AbstractRegisterLoginComponent(RegisterLoginFuncInterface registerLoginFuncInterface) {
        validate(registerLoginFuncInterface);
        funcInterface = registerLoginFuncInterface;
    }

    protected final void validate(RegisterLoginFuncInterface registerLoginFuncInterface) {
        assert registerLoginFuncInterface != null;
        if (!(registerLoginFuncInterface instanceof RegisterLoginFuncInterface)) {
            throw new UnsupportedOperationException("Unknown register/ login function type!");
        }
    }

    public abstract String login(String username, String password);
    public abstract String register(UserInfo userInfo);
    public abstract boolean checkUserExists(String userName);
    public abstract String login3rd(HttpServletRequest request);
}
