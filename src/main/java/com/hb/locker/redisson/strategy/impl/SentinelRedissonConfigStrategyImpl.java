package com.hb.locker.redisson.strategy.impl;

import com.hb.locker.redisson.config.RedissonProperties;
import com.hb.locker.redisson.constant.RedissonConstant;
import com.hb.locker.redisson.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 哨兵集群方式
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class SentinelRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentinelRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            /**
             * 设置redis配置文件sentinel.conf配置的sentinel别名
             */
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (password != null && !"".equals(password)) {
                config.useSentinelServers().setPassword(password);
            }
            /**
             * 设置sentinel节点的服务IP和端口
             */
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(RedissonConstant.REDIS_CONNECTION_PREFIX + addrTokens[i]);
            }
            LOGGER.info("sentinel redisson config init, redisAddress: " + address);
        } catch (Exception e) {
            LOGGER.error("sentinel redisson config init error: {}", e);
        }
        return config;
    }
}
