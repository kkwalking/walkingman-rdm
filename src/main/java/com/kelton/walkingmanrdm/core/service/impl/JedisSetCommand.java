package com.kelton.walkingmanrdm.core.service.impl;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisSetCommand;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * @Author zhouzekun
 * @Date 2024/4/22 17:41
 */
public class JedisSetCommand implements RedisSetCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StringUtils.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public Long sadd(RedisConnectionInfo connectionInfo, String key, String... members) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sadd(key, members);
        }
    }

    @Override
    public Long scard(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.scard(key);
        }
    }

    @Override
    public Set<String> sdiff(RedisConnectionInfo connectionInfo, String... keys) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sdiff(keys);
        }
    }

    @Override
    public Long sdiffstore(RedisConnectionInfo connectionInfo, String destination, String... keys) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sdiffstore(destination, keys);
        }
    }

    @Override
    public Set<String> sinter(RedisConnectionInfo connectionInfo, String... keys) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sinter(keys);
        }
    }

    @Override
    public Long sinterstore(RedisConnectionInfo connectionInfo, String destination, String... keys) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sinterstore(destination, keys);
        }
    }

    @Override
    public Boolean sismember(RedisConnectionInfo connectionInfo, String key, String member) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sismember(key, member);
        }
    }

    @Override
    public Set<String> smembers(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.smembers(key);
        }
    }

    @Override
    public List<Boolean> smismember(RedisConnectionInfo connectionInfo, String key, String... members) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.smismember(key, members);
        }
    }

    @Override
    public Long smove(RedisConnectionInfo connectionInfo, String source, String destination, String member) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.smove(source, destination, member);
        }
    }

    @Override
    public String spop(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.spop(key);
        }
    }

    @Override
    public Set<String> spop(RedisConnectionInfo connectionInfo, String key, long count) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.spop(key, count);
        }
    }

    @Override
    public String srandmember(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.srandmember(key);
        }
    }

    @Override
    public List<String> srandmember(RedisConnectionInfo connectionInfo, String key, int count) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.srandmember(key, count);
        }
    }

    @Override
    public Long srem(RedisConnectionInfo connectionInfo, String key, String... members) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.srem(key, members);
        }
    }

    @Override
    public Set<String> sunion(RedisConnectionInfo connectionInfo, String... keys) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sunion(keys);
        }
    }

    @Override
    public ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sscan(key, cursor);
        }
    }

    @Override
    public ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor, int count) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sscan(key, cursor, new ScanParams().count(count));
        }
    }

    @Override
    public ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.sscan(key, cursor, new ScanParams().match(pattern));
        }
    }
}
