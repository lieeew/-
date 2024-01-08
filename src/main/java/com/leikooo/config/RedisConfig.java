package com.leikooo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        setSerializeConfig(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    private void setSerializeConfig(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory redisConnectionFactory) {
        // 普通的 key value 采用 StringRedisSerializer 进行序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //解决查询缓存转换异常的问题
        Jackson2JsonRedisSerializer<?> redisValueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisValueSerializer.setObjectMapper(objectMapper);

        //普通value与hash类型的value采用jackson方式进行序列化
        redisTemplate.setValueSerializer(redisValueSerializer);
        redisTemplate.setHashValueSerializer(redisValueSerializer);
        redisTemplate.afterPropertiesSet();
    }
}
