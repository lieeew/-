package com.leikooo.service;

import com.leikooo.pojo.UserInfo;
import com.leikooo.repo.UserRepository;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Date;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public String login(String account, String password) {
        assert StringUtils.isAnyEmpty(account, password) : "account or password do not follow our request";
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account / password error !";
        }
        return "loginSuccess";
    }

    public String register(UserInfo userInfo) {
        if (checkUserExists(userInfo.getUserName())) {
            throw new RuntimeException("user already exists");
        }
        userInfo.setCreatData(new Date());
        userRepository.save(userInfo);
        return "Register success";
    }

    protected boolean checkUserExists(String userName) {
        UserInfo byUserName = userRepository.findByUserName(userName);
        return byUserName != null;
    }
}