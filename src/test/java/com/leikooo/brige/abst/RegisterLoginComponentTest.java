package com.leikooo.brige.abst;

import com.leikooo.brige.function.RegisterLoginByDefault;
import com.leikooo.brige.function.RegisterLoginByGitee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @data 2024/1/4
 * @description
 */
class RegisterLoginComponentTest {

    @Test
    void login3rd() {
        RegisterLoginComponent registerLoginComponent = new RegisterLoginComponent(new RegisterLoginByGitee());
        registerLoginComponent.login3rd(null);
    }
}