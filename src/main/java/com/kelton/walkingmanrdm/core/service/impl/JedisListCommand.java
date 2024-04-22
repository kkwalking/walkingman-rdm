package com.kelton.walkingmanrdm.core.service.impl;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisListCommand;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisListCommand implements RedisListCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StringUtils.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public List<String> lrange(RedisConnectionInfo connectInfo, String key, long start, long stop) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lrange(key, start, stop);
        }
    }

    @Override
    public Long lrem(RedisConnectionInfo connectInfo, String key, long count, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lrem(key, count, value);
        }
    }

    @Override
    public Long llen(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.llen(key);
        }
    }

    @Override
    public String lpop(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lpop(key);
        }
    }

    @Override
    public List<String> lpop(RedisConnectionInfo connectInfo, String key, long count) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lpop(key, (int) count);
        }
    }

    @Override
    public Long lpush(RedisConnectionInfo connectInfo, String key, String... values) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lpush(key, values);
        }
    }

    @Override
    public String lset(RedisConnectionInfo connectInfo, String key, long index, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.lset(key, index, value);
        }
    }

    @Override
    public String rpop(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.rpop(key);
        }
    }

    @Override
    public List<String> rpop(RedisConnectionInfo connectInfo, String key, long count) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.rpop(key, (int) count);
        }
    }

    @Override
    public Long rpush(RedisConnectionInfo connectInfo, String key, String... values) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.rpush(key, values);
        }
    }
}