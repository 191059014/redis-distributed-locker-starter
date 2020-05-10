package com.hb.locker.redisson;

import com.hb.locker.redisson.config.RedissonBuilder;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson的分布式锁
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class RedissonLocker {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLocker.class);

    /**
     * redisson构造器
     */
    private RedissonBuilder redissonBuilder;

    /**
     * 构造方法
     */
    public RedissonLocker(RedissonBuilder redissonBuilder) {
        this.redissonBuilder = redissonBuilder;
    }

    /**
     * 获取锁
     *
     * @param lockKey       锁名称
     * @param expireSeconds 过期时间
     * @return true为成功获取锁
     */
    public boolean getLock(String lockKey, long expireSeconds) {
        RLock rLock = redissonBuilder.getRedisson().getLock(lockKey);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(0, expireSeconds, TimeUnit.SECONDS);
            if (getLock) {
                LOGGER.info("get redisson distributed lock success, lockKey: {}", lockKey);
            } else {
                LOGGER.info("get redisson distributed lock failed, lockKey: {}", lockKey);
            }
        } catch (InterruptedException e) {
            LOGGER.error("get redisson distributed lock error, lockKey: {}, e: {}" + lockKey, e);
            return false;
        }
        return getLock;
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁名称
     */
    public void releaseLock(String lockKey) {
        redissonBuilder.getRedisson().getLock(lockKey).unlock();
    }

}
