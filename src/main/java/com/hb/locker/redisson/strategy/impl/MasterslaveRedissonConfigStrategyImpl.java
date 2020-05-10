package com.hb.locker.redisson.strategy.impl;

import com.hb.locker.redisson.config.RedissonProperties;
import com.hb.locker.redisson.constant.RedissonConstant;
import com.hb.locker.redisson.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从
 * 连接方式：主节点，子节点，子节点，格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class MasterslaveRedissonConfigStrategyImpl implements RedissonConfigStrategy {

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
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            /**
             * 设置主节点ip
             */
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (password != null && !"".equals(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            /**
             * 设置从节点，移除第一个节点，默认第一个为主节点
             */
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(RedissonConstant.REDIS_CONNECTION_PREFIX + addrToken);
            }
            slaveList.remove(0);
            config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
            LOGGER.info("masterslave redisson config init, redisAddress: " + address);
        } catch (Exception e) {
            LOGGER.error("masterslave redisson config init error: {}", e);
        }
        return config;
    }

}
