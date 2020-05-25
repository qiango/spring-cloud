package com.core.base.util;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * redicache 工具类
 */
public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys 删除redis对应key的值
     */
    public void remove(String... keys) {
        for (String key : keys) {
            if (exists(key)) {
                redisTemplate.delete(key);
            }
        }
    }

//    /**
//     * 批量删除key
//     *
//     * @param pattern 批量删除的正则
//     */
//    public void removePattern(String pattern) {
//        Set<String> keys = redisTemplate.keys(pattern);
//        if (keys != null && keys.size() > 0)
//            redisTemplate.delete(keys);
//    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key redis 中的key
     */
    private boolean exists(String key) {
        Boolean temp = redisTemplate.hasKey(key);
        return temp == null ? false : temp;
    }

    /**
     * 读取缓存
     *
     * @param key redis 中的key
     */
    public Object get(String key) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入缓存
     *
     * @param key   redis 中的key
     * @param value redis 中的值
     */
    public boolean set(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key        redis 中的key
     * @param value      redis 中的值
     * @param expireTime 有效时间
     */
    public boolean set(String key, Object value, Long expireTime) {
        return set(key, value, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存
     *
     * @param key        redis 中的key
     * @param value      redis 中的值
     * @param expireTime 有效时间
     */
    public boolean set(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean boundValueOps(String key, long value) {
        boolean result = false;
        try {
            redisTemplate.boundValueOps(key).increment(value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key   redis 中的key
     * @param value redis 中的值
     */
    public boolean send(String key, Object value) {
        boolean result = false;
        try {
            stringRedisTemplate.convertAndSend(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
