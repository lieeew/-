package com.leikooo.brige.function;

import com.leikooo.pojo.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
public interface RegisterLoginFuncInterface {
    String login(String account, String password);
    String register(UserInfo userInfo);
    boolean checkLogin(String username);
    String login3rd(HttpServletRequest request);
}
