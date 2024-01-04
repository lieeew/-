package com.leikooo.brige.function;

import com.leikooo.pojo.UserInfo;
import com.leikooo.repo.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Component
public class RegisterLoginByDefault extends AbstractLoginRegisterFunc implements RegisterLoginFuncInterface {
    @Resource
    private UserRepository userRepository;

    @Override
    public String login(String account, String password) {
        return super.commonLogin(account, password, userRepository);
    }

    @Override
    public String register(UserInfo userInfo) {
        return super.commonRegister(userInfo, userRepository);
    }

    @Override
    public boolean checkLogin(String username) {
        return super.commonCheckUserExist(username, userRepository);
    }
}
