package com.kelton.walkingmanrdm.core.service.impl;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisStringCommand;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * @Author zhouzekun
 * @Date 2024/4/22 15:14
 */
public class JedisStringComand implements RedisStringCommand {


    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StringUtils.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public String set(RedisConnectionInfo connectInfo, String key, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.set(key, value);
        }
    }

    @Override
    public String get(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.get(key);
        }
    }

    @Override
    public Long strlen(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.strlen(key);
        }
    }

    @Override
    public String setex(RedisConnectionInfo connectInfo, String key, long expire, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.setex(key, expire, value);
        }
    }
}
