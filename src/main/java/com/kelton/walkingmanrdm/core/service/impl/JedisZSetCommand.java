package com.kelton.walkingmanrdm.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisZSetCommand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisZSetCommand implements RedisZSetCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StrUtil.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public Long zadd(RedisConnectionInfo connectionInfo, String key, double score, String member) {
        try (Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port())) {
            return jedis.zadd(key, score, member);
        }
    }

    @Override
    public Long zadd(RedisConnectionInfo connectionInfo, String key, Map<String, Double> scoreMembers) {
        try (Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port())) {
            return jedis.zadd(key, scoreMembers);
        }
    }

    @Override
    public Long zrem(RedisConnectionInfo connectionInfo, String key, String... members) {
        try (Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port())) {
            return jedis.zrem(key, members);
        }
    }

    @Override
    public List<String> zrange(RedisConnectionInfo connectionInfo, String key, long start, long stop) {
        try (Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port())) {
            return jedis.zrange(key, start, stop);
        }
    }


    @Override
    public Double zincrby(RedisConnectionInfo connectionInfo, String key, double increment, String member) {
        return null;
    }

    @Override
    public Long zrank(RedisConnectionInfo connectionInfo, String key, String member) {
        return null;
    }

    @Override
    public Long zrevrank(RedisConnectionInfo connectionInfo, String key, String member) {
        return null;
    }

    @Override
    public Set<String> zrevrange(RedisConnectionInfo connectionInfo, String key, long start, long stop) {
        return null;
    }


    /**
     * 获取有序集key中指定区间内的成员
     * 这些成员按score值递增(从小到大)次序排列，并带有成员的score值
     * @param connectionInfo
     * @param key
     * @param start
     * @param stop
     * @return
     */
    @Override
    public List<Tuple> zrangeWithScores(RedisConnectionInfo connectionInfo, String key, long start, long stop) {
        try (Jedis jedis = getConnection(connectionInfo)) {

            return jedis.zrangeWithScores(key, start, stop);
        }
    }


    @Override
    public Set<Tuple> zrevrangeWithScores(RedisConnectionInfo connectionInfo, String key, long start, long stop) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(RedisConnectionInfo connectionInfo, String key, double min, double max) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(RedisConnectionInfo connectionInfo, String key, double min, double max) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(RedisConnectionInfo connectionInfo, String key, double max, double min) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(RedisConnectionInfo connectionInfo, String key, double max, double min) {
        return null;
    }

    @Override
    public Long zcard(RedisConnectionInfo connectionInfo, String key) {
        return null;
    }

    @Override
    public Double zscore(RedisConnectionInfo connectionInfo, String key, String member) {
        return null;
    }

    @Override
    public Long zremrangeByRank(RedisConnectionInfo connectionInfo, String key, long start, long stop) {
        return null;
    }

    @Override
    public Long zremrangeByScore(RedisConnectionInfo connectionInfo, String key, double start, double stop) {
        return null;
    }

    @Override
    public Long zunionstore(RedisConnectionInfo connectionInfo, String destination, String... keys) {
        return null;
    }

    @Override
    public Long zinterstore(RedisConnectionInfo connectionInfo, String destination, String... keys) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, int count) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern, int count) {
        return null;
    }
}