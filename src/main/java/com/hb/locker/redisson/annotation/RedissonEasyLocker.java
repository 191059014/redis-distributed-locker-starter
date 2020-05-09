package com.hb.locker.redisson.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedissonEasyLocker {

    /**
     * 分布式锁名称
     */
    String value();

    /**
     * 锁超时时间，默认十秒
     */
    int expireSeconds() default 10;

}


