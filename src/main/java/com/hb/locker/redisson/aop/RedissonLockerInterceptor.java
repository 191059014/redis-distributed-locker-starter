package com.hb.locker.redisson.aop;

import com.hb.locker.redisson.RedissonLocker;
import com.hb.locker.redisson.annotation.RedissonEasyLocker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 打开分布式锁注解
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
@Aspect
public class RedissonLockerInterceptor {

    /**
     * the constant logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLockerInterceptor.class);

    /**
     * redis限流器
     */
    private RedissonLocker redissonLocker;

    /**
     * 构造方法
     *
     * @param redissonLocker redis分布式锁
     */
    public RedissonLockerInterceptor(RedissonLocker redissonLocker) {
        this.redissonLocker = redissonLocker;
    }

    /**
     * 限流统一拦截
     *
     * @param pjp 方法执行上下文
     * @return 方法执行结果
     */
    @Around("execution(public * *(..)) && @annotation(com.hb.locker.redisson.annotation.RedissonEasyLocker)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedissonEasyLocker lockAnnotation = method.getAnnotation(RedissonEasyLocker.class);
        String lockName = lockAnnotation.lockKey();
        int expireSeconds = lockAnnotation.expireSeconds();
        if (redissonLocker.getLock(lockName, expireSeconds)) {
            try {
                LOGGER.info("获取分布式锁成功[{}], 开始处理业务...", lockName);
                return pjp.proceed();
            } catch (Throwable throwable) {
                LOGGER.error("获取分布式锁异常[{}], e: {}", lockName, throwable);
            } finally {
                redissonLocker.releaseLock(lockName);
                LOGGER.info("释放分布式锁完成[{}]", lockName);
            }
        } else {
            LOGGER.info("获取分布式锁失败[{}], 停止处理业务...", lockName);
        }
        return null;
    }

}
