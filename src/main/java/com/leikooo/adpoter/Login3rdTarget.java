package com.leikooo.adpoter;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/4
 * @description
 */
public interface Login3rdTarget {

    String loginByGitee(String code, String state);
    String loginByWeChat(HttpServletRequest request);
}
