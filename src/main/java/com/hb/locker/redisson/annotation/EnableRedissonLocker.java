package com.hb.locker.redisson.annotation;

import com.hb.locker.redisson.config.RedissonLockerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打开分布式锁注解
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RedissonLockerAutoConfiguration.class)
public @interface EnableRedissonLocker {

}
