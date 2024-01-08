package com.leikooo.adpoter;

import com.alibaba.fastjson2.JSONObject;
import com.leikooo.pojo.UserInfo;
import com.leikooo.service.UserService;
import com.leikooo.util.HttpClientUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/4
 * @description
 */
@Component
public class Login3rdAdapter extends UserService implements Login3rdTarget{

    @Value("${gitee.state}")
    private String giteeState;

    @Value("${gitee.token.url}")
    private String giteeTokenUrl;

    @Value("${gitee.user.url}")
    private String giteeUserUrl;

    @Value("${gitee.user.prefix}")
    private String giteePrefix;

    @Override
    public String loginByGitee(String code, String state) {
        if (!giteeState.equals(state)) {
            throw new UnsupportedOperationException("Invalid state !");
        }
        String tokenUrl = giteeTokenUrl.concat(code);
        JSONObject tokenResponse = HttpClientUtils.execute(tokenUrl, HttpMethod.POST);
        assert tokenResponse != null : "tokenResponse is null!";
        String token = tokenResponse.getString("access_token");
        String userUrl = giteeUserUrl.concat(token);
        JSONObject userResponse = HttpClientUtils.execute(userUrl, HttpMethod.GET);
        assert userResponse != null : "userResponse is null!";
        String userName = giteePrefix + userResponse.getString("name");
        // password 和 username 保持一致
        String password = userName;
        return autoRegister3rdAndLogin(userName, password);
    }

    private String autoRegister3rdAndLogin(String userName, String password) {
        if (StringUtils.isAnyEmpty(userName, password)) {
            throw new UnsupportedOperationException("Invalid userName or password!");
        }
        UserInfo userInfo = UserInfo.builder().userPassword(password).userName(userName).build();
        if (!checkUserExists(userName)) {
            register(userInfo);
        }
        return login(userName, password);
    }

    @Override
    public String loginByWeChat(HttpServletRequest request) {
        return null;
    }
}
