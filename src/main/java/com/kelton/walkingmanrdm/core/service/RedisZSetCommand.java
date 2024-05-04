package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisZSetCommand;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisZSetCommand {

    public RedisZSetCommand INSTANCE = new JedisZSetCommand();

    Long zadd(RedisConnectionInfo connectionInfo, String key, double score, String member);

    Long zadd(RedisConnectionInfo connectionInfo, String key, Map<String, Double> scoreMembers);

    Double zincrby(RedisConnectionInfo connectionInfo, String key, double increment, String member);

    Long zrank(RedisConnectionInfo connectionInfo, String key, String member);

    Long zrevrank(RedisConnectionInfo connectionInfo, String key, String member);

    List<String> zrange(RedisConnectionInfo connectionInfo, String key, long start, long stop);

    Set<String> zrevrange(RedisConnectionInfo connectionInfo, String key, long start, long stop);


    /**
     * 获取有序集key中指定区间内的成员
     * 这些成员按score值递增(从小到大)次序排列，并带有成员的score值
     * @param connectionInfo
     * @param key
     * @param start
     * @param stop
     * @return
     */
    List<Tuple> zrangeWithScores(RedisConnectionInfo connectionInfo, String key, long start, long stop);

    Set<Tuple> zrevrangeWithScores(RedisConnectionInfo connectionInfo, String key, long start, long stop);

    Set<String> zrangeByScore(RedisConnectionInfo connectionInfo, String key, double min, double max);

    Set<Tuple> zrangeByScoreWithScores(RedisConnectionInfo connectionInfo, String key, double min, double max);

    Set<String> zrevrangeByScore(RedisConnectionInfo connectionInfo, String key, double max, double min);

    Set<Tuple> zrevrangeByScoreWithScores(RedisConnectionInfo connectionInfo, String key, double max, double min);

    Long zcard(RedisConnectionInfo connectionInfo, String key);

    Double zscore(RedisConnectionInfo connectionInfo, String key, String member);

    Long zrem(RedisConnectionInfo connectionInfo, String key, String... members);

    Long zremrangeByRank(RedisConnectionInfo connectionInfo, String key, long start, long stop);

    Long zremrangeByScore(RedisConnectionInfo connectionInfo, String key, double start, double stop);

    Long zunionstore(RedisConnectionInfo connectionInfo, String destination, String... keys);

    Long zinterstore(RedisConnectionInfo connectionInfo, String destination, String... keys);

    ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor);

    ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, int count);

    ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern);

    ScanResult<Tuple> zscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern, int count);

}