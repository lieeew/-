package com.leikooo.service;

import com.leikooo.duty.AbstractBusinessHandler;
import com.leikooo.duty.CityHandler;
import com.leikooo.duty.builder.HandlerEnum;
import com.leikooo.pojo.BusinessLaunch;
import com.leikooo.pojo.UserInfo;
import com.leikooo.repo.BusinessLaunchRepository;
import com.leikooo.repo.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Slf4j
@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private BusinessLaunchRepository businessLaunchRepository;

//    @Value("${duty.chain}")
    private String handlerType;

    // 记录当前 handlerType 的配置
    private String currentHandlerType;

    // 记录当前的责任链头，如果没有修改直接返回
    private AbstractBusinessHandler currentHandler;

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

    private AbstractBusinessHandler buildChain() {
        if (handlerType == null) {
            return null;
        }
        // 如果是第一次配置那么把对应的 handlerType 记录下来
        if (currentHandlerType == null) {
            this.currentHandlerType = this.handlerType;
        }
        if (this.currentHandlerType.equals(handlerType) && currentHandler != null) {
            return currentHandler;
        } else {
            // 配置进行了修改或者是第一次的情况
            log.info("配置有修改或者初始化，组件链条组装!");
            synchronized (this) {
                try {
                    // 创建哑节点
                    AbstractBusinessHandler dummyHeadHandler = new CityHandler();
                    // 创建前置节点初始为哑节点
                    AbstractBusinessHandler preHandler = dummyHeadHandler;
                    String[] typeList = handlerType.split(",");
                    for (String handlerType : typeList) {
                        AbstractBusinessHandler handler = (AbstractBusinessHandler) Class.forName(HandlerEnum.valueOf(handlerType).getValue()).getDeclaredConstructor().newInstance();
                        preHandler.nextHandler = handler;
                        preHandler = handler;
                    }
                    this.currentHandler = dummyHeadHandler;
                    this.currentHandlerType = this.handlerType;
                    return currentHandler;
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                         IllegalAccessException | InvocationTargetException error) {
                    throw new UnsupportedOperationException();
                }
            }
        }
    }

    public List<BusinessLaunch> filterBusinessLaunch(String sex, String city, String product) {
        List<BusinessLaunch> businessLaunches = businessLaunchRepository.findAll();
        return Objects.requireNonNull(buildChain()).processHandler(businessLaunches, city, sex, product);
    }
}