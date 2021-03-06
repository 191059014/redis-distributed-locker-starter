package com.hb.locker.redisson.strategy.impl;

import com.hb.locker.redisson.config.RedissonProperties;
import com.hb.locker.redisson.constant.RedissonConstant;
import com.hb.locker.redisson.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集群
 * 地址格式：
 * cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
 * 格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class ClusterRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            /**
             * 设置cluster节点的服务IP和端口
             */
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers().addNodeAddress(RedissonConstant.REDIS_CONNECTION_PREFIX + addrTokens[i]);
                if (password != null && !"".equals(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            LOGGER.info("cluster redisson config init, redisAddress: " + address);
        } catch (Exception e) {
            LOGGER.error("cluster redisson config init error: {}", e);
        }
        return config;
    }
}
