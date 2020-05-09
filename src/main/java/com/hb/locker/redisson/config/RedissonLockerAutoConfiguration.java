package com.hb.locker.redisson.config;

import com.hb.locker.redisson.RedissonLocker;
import com.hb.locker.redisson.aop.RedissonLockerInterceptor;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonLockerAutoConfiguration {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLockerAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 1)
    public RedissonBuilder redissonBuilder(RedissonProperties redissonProperties) {
        return new RedissonBuilder(redissonProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 2)
    public RedissonLocker redissonLocker(RedissonBuilder redissonBuilder) {
        RedissonLocker redissonLocker = new RedissonLocker(redissonBuilder);
        LOGGER.info("redissonLocker init complete");
        return redissonLocker;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order(value = 3)
    public RedissonLockerInterceptor redisLimiterInterceptor(RedissonLocker redissonLocker) {
        return new RedissonLockerInterceptor(redissonLocker);
    }

}

