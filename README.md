# redis-distributed-locker-starter
自定义的基于redisson的分布式锁
## 引入方法
```
首先下载项目，然后依赖pom文件，在启动类上加上注解@EnableRedissonLocker，加上对应的配置，最后根据场景决定使用注解的方式还是注入的方式。
```
- 依赖pom文件
```
<dependency>
   <groupId>com.hb</groupId>
   <artifactId>redis-distributed-locker-starter</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</dependency>
```
- 配置
```
redisson.config.server.address=ip1,ip2,ip3 // redis主机地址，ip：port，有多个用半角逗号分隔
redisson.config.server.type=standalone // redis连接方式：standalone-单节点部署方式；sentinel-哨兵部署方式；cluster-集群方式；masterslave-主从部署方式；
redisson.config.server.password=123456 // redis密码
redisson.config.server.database=0 // redis数据库索引
```
- 注解方式
```
@GetMapping("/testDistributedLocker")
@RedissonEasyLocker(lockKey = "distributedLockKey", expireSeconds = 15)
public void testDistributedLocker() {
    System.out.println("获取分布式锁成功");
}
```
- 注入方式
```
@Autowired
private RedissonLocker redissonLocker;

@GetMapping("/testDistributedLocker")
public void testDistributedLocker() {
    String lockKey = "distributedLockKey";
    if (redissonLocker.getLock(lockName, 15)) {
        System.out.println("获取分布式锁成功");
        try {
            /**
             * do business...
             */
        } finally {
            redissonLocker.releaseLock(lockName);
            System.out.println("释放锁");
        }
    } else {
        System.out.println("获取分布式锁失败");
    }
}
```
