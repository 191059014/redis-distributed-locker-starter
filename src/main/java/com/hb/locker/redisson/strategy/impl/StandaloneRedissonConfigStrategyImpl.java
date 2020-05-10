package com.hb.locker.redisson.strategy.impl;

import com.hb.locker.redisson.config.RedissonProperties;
import com.hb.locker.redisson.constant.RedissonConstant;
import com.hb.locker.redisson.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单机方式
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class StandaloneRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = RedissonConstant.REDIS_CONNECTION_PREFIX + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (password != null && !"".equals(password)) {
                config.useSingleServer().setPassword(password);
            }
            LOGGER.info("standalone redisson config init, redisAddress: " + address);
        } catch (Exception e) {
            LOGGER.error("standalone redisson config init error: {}", e);
        }
        return config;
    }
}
