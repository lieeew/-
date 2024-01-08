package com.leikooo.util;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Component
public class RedisCommonProcessor {
    @Resource
    private RedisTemplate redisTemplate;

    // 通过 key 获取 value
    public Object get(String key) {
        if (key == null) {
            throw new UnsupportedOperationException();
        }
        return redisTemplate.opsForValue().get(key);
    }

    // 保存 key value
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 保存 k v 并且支持过期时间
    public void set(String key, Object value, long timeout) {
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
    }
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
