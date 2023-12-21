package com.example.basic.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Configuration
@Slf4j
public class RedisUtil {

    @Autowired
    private static RedisTemplate redisTemplate;

    private static ThreadLocal<String> redisLockValue = new ThreadLocal<>();

    public static void set(String v) { redisLockValue.set(v); }

    public static String get() {
        return redisLockValue.get();
    }

    public static void clean() {
        redisLockValue.remove();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    public static boolean acquireLock(String lockKey,  long expireTime) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        //线程标识位
        String uuId = UUID.randomUUID().toString() + System.currentTimeMillis();
        set(uuId);
        // SETNX命令，如果key不存在，则设置key的值为value，同时返回1；如果key已经存在，则不做任何操作，返回0。
        Boolean acquired = operations.setIfAbsent(lockKey, uuId, expireTime, TimeUnit.MILLISECONDS);
        return acquired != null && acquired;
    }

    public static void releaseLock(String lockKey) {
        String storedValue = (String) redisTemplate.opsForValue().get(lockKey);
        String value=get();
        if (ObjectUtils.isNotEmpty(lockKey) && ObjectUtils.isNotEmpty(storedValue)
        && ObjectUtils.isNotEmpty(value) && storedValue.equals(value)) {
            // 释放锁，删除key
            redisTemplate.delete(lockKey);
        }
    }
}
