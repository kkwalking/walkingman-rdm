package com.kelton.walkingmanrdm.core.service.impl;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisHashCommand;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JedisHashCommand implements RedisHashCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StringUtils.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }
    @Override
    public String hget(RedisConnectionInfo connectInfo, String key, String field) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hget(key, field);
        }
    }

    @Override
    public Map<String, String> hgetall(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public List<String> hkeys(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return new ArrayList<>(jedis.hkeys(key));
        }
    }

    @Override
    public Long hlen(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hlen(key);
        }
    }

    @Override
    public String hmset(RedisConnectionInfo connectInfo, String key, Map<String, String> map) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hmset(key, map);
        }
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(RedisConnectionInfo connectInfo, String key,
                                                       String scanCursor, ScanParams scanParams) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hscan(key, scanCursor, scanParams);
        }
    }

    @Override
    public Long hset(RedisConnectionInfo connectInfo, String key, String field, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hset(key, field, value);
        }
    }

    @Override
    public Long hsetnx(RedisConnectionInfo connectInfo, String key, String field, String value) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hsetnx(key, field, value);
        }
    }

    @Override
    public Long hdel(RedisConnectionInfo connectInfo, String key, String... fields) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return jedis.hdel(key, fields);
        }
    }

    @Override
    public List<String> hvals(RedisConnectionInfo connectInfo, String key) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return new ArrayList<>(jedis.hvals(key));
        }
    }
}