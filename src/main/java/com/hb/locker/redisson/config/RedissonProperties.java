package com.hb.locker.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * redisson配置
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
@ConfigurationProperties(prefix = "redisson.lock.config")
public class RedissonProperties {

    /**
     * redis主机地址，ip：port，有多个用半角逗号分隔
     */
    private String address;

    /**
     * 连接类型
     */
    private String type;

    /**
     * 连接密码
     */
    private String password;

    /**
     * 数据库索引
     */
    private Integer database;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

}
