package com.hb.locker.redisson.config;

import com.hb.locker.redisson.config.strategy.ClusterRedissonConfigStrategyImpl;
import com.hb.locker.redisson.config.strategy.MasterslaveRedissonConfigStrategyImpl;
import com.hb.locker.redisson.config.strategy.RedissonConfigStrategy;
import com.hb.locker.redisson.config.strategy.SentinelRedissonConfigStrategyImpl;
import com.hb.locker.redisson.config.strategy.StandaloneRedissonConfigStrategyImpl;
import com.hb.locker.redisson.enumutil.RedisConnectionType;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * redisson构建器
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public class RedissonBuilder {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonBuilder.class);

    /**
     * redisson config
     */
    private Config config;

    /**
     * redisson
     */
    private Redisson redisson;

    /**
     * 构造方法
     *
     * @param redissonProperties redisson配置
     */
    RedissonBuilder(RedissonProperties redissonProperties) {
        config = this.createConfigStragy(redissonProperties);
        redisson = (Redisson) Redisson.create(config);
    }

    public Redisson getRedisson() {
        return redisson;
    }

    /**
     * 不通策略创建redisson的config
     *
     * @param redissonProperties redisson配置
     * @return redisson的config
     */
    private Config createConfigStragy(RedissonProperties redissonProperties) {
        Objects.requireNonNull(redissonProperties, "redisson config cannot be null");
        Objects.requireNonNull(redissonProperties.getAddress(), "redisson.lock.server.address cannot be null");
        Objects.requireNonNull(redissonProperties.getType(), "redisson.lock.server.password cannot be null");
        Objects.requireNonNull(redissonProperties.getDatabase(), "redisson.lock.server.database cannot be null");
        String connectionType = redissonProperties.getType();
        RedissonConfigStrategy redissonConfigStrategy = null;
        /**
         * 不同连接类型采取不通策略
         */
        if (connectionType.equals(RedisConnectionType.STANDALONE.getConnectionType())) {
            redissonConfigStrategy = new StandaloneRedissonConfigStrategyImpl();
        } else if (connectionType.equals(RedisConnectionType.SENTINEL.getConnectionType())) {
            redissonConfigStrategy = new SentinelRedissonConfigStrategyImpl();
        } else if (connectionType.equals(RedisConnectionType.CLUSTER.getConnectionType())) {
            redissonConfigStrategy = new ClusterRedissonConfigStrategyImpl();
        } else if (connectionType.equals(RedisConnectionType.MASTERSLAVE.getConnectionType())) {
            redissonConfigStrategy = new MasterslaveRedissonConfigStrategyImpl();
        } else {
            throw new IllegalArgumentException("create redisson config failed, reason: error connectionType:" + connectionType);
        }
        return redissonConfigStrategy.createRedissonConfig(redissonProperties);
    }

}


