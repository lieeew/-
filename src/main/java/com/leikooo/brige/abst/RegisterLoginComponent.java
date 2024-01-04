package com.leikooo.brige.abst;

import com.leikooo.brige.function.RegisterLoginFuncInterface;
import com.leikooo.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/4
 * @description
 */
public class RegisterLoginComponent extends AbstractRegisterLoginComponent {

    public RegisterLoginComponent(RegisterLoginFuncInterface registerLoginFuncInterface) {
        super(registerLoginFuncInterface);
    }

    @Override
    public String login(String username, String password) {
        return super.funcInterface.login(username, password);
    }

    @Override
    public String register(UserInfo userInfo) {
        return super.funcInterface.register(userInfo);
    }

    @Override
    public boolean checkUserExists(String userName) {
        return super.funcInterface.checkLogin(userName);
    }

    @Override
    public String login3rd(HttpServletRequest request) {
        return super.funcInterface.login3rd(request);
    }
}
