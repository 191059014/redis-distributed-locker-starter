package com.hb.locker.redisson.enumutil;

/**
 * redis连接类型
 *
 * @author Mr.huang
 * @since 2020/5/9 16:41
 */
public enum RedisConnectionType {

    STANDALONE("standalone", "单节点部署方式"),
    SENTINEL("sentinel", "哨兵部署方式"),
    CLUSTER("cluster", "集群方式"),
    MASTERSLAVE("masterslave", "主从部署方式");

    /**
     * 连接类型
     */
    private String connectionType;
    /**
     * 连接描述
     */
    private String connectionDesc;

    RedisConnectionType(String connectionType, String connectionDesc) {
        this.connectionType = connectionType;
        this.connectionDesc = connectionDesc;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getConnectionDesc() {
        return connectionDesc;
    }

}
