package com.leikooo.controller;

import com.leikooo.pojo.BusinessLaunch;
import com.leikooo.pojo.UserInfo;
import com.leikooo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public String login(String account, String password) {
        return userService.login(account, password);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserInfo userInfo) {
        return userService.register(userInfo);
    }

    @PostMapping("/business/launch")
    public List<BusinessLaunch> filterBusinessLaunch(@RequestParam("city") String city, @RequestParam("sex") String sex, @RequestParam("product") String product) {
        return userService.filterBusinessLaunch(sex, city, product);
    }
}