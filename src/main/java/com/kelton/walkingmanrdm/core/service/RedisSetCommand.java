package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisSetCommand;
import redis.clients.jedis.resps.ScanResult;
import java.util.List;
import java.util.Set;

public interface RedisSetCommand {

    RedisSetCommand INSTANT = new JedisSetCommand();

    Long sadd(RedisConnectionInfo connectionInfo, String key, String... members);

    Long scard(RedisConnectionInfo connectionInfo, String key);

    Set<String> sdiff(RedisConnectionInfo connectionInfo, String... keys);

    Long sdiffstore(RedisConnectionInfo connectionInfo, String destination, String... keys);

    Set<String> sinter(RedisConnectionInfo connectionInfo, String... keys);

    Long sinterstore(RedisConnectionInfo connectionInfo, String destination, String... keys);

    Boolean sismember(RedisConnectionInfo connectionInfo, String key, String member);

    Set<String> smembers(RedisConnectionInfo connectionInfo, String key);

    /**
     * Available since: 6.2.0
     * could not use directly in redis which version is before 6.2.0
     * @param connectionInfo
     * @param key
     * @param members
     * @return
     */
    List<Boolean> smismember(RedisConnectionInfo connectionInfo, String key, String... members);

    Long smove(RedisConnectionInfo connectionInfo, String source, String destination, String member);

    String spop(RedisConnectionInfo connectionInfo, String key);

    Set<String> spop(RedisConnectionInfo connectionInfo, String key, long count);

    String srandmember(RedisConnectionInfo connectionInfo, String key);

    List<String> srandmember(RedisConnectionInfo connectionInfo, String key, int count);

    Long srem(RedisConnectionInfo connectionInfo, String key, String... members);

    Set<String> sunion(RedisConnectionInfo connectionInfo, String... keys);

    ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor);

    ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor, int count);

    ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern);

    ScanResult<String> sscan(RedisConnectionInfo connectionInfo, String key, String cursor, String pattern, int count);
}