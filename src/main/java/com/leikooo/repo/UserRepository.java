package com.leikooo.repo;

import com.leikooo.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    // 根据用户名查询用户信息
    UserInfo findByUserName(String userName);

    // 根据用户名和密码查询信息
    UserInfo findByUserNameAndUserPassword(String userName, String password);
}