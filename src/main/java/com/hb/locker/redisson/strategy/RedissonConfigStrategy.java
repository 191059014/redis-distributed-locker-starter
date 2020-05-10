package com.hb.locker.redisson.strategy;

import com.hb.locker.redisson.config.RedissonProperties;
import org.redisson.config.Config;

/**
 * redisson config创建策略
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public interface RedissonConfigStrategy {

    /**
     * 根据不同的Redis配置策略创建对应的Config
     *
     * @param redissonProperties redisson配置
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
