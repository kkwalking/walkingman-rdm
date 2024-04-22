package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisHashCommand;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Map;

public interface RedisHashCommand {

    RedisHashCommand INSTANT = new JedisHashCommand();

    String hget(RedisConnectionInfo connectInfo, String key, String field);

    Map<String, String> hgetall(RedisConnectionInfo connectInfo, String key);

    List<String> hkeys(RedisConnectionInfo connectInfo, String key);

    Long hlen(RedisConnectionInfo connectInfo, String key);

    String hmset(RedisConnectionInfo connectInfo, String key, Map<String, String> map);

    // 注意：Jedis 的 hscan 返回一个ScanResult<Map.Entry<String, String>>对象，而不是MapScanCursor
    ScanResult<Map.Entry<String, String>> hscan(RedisConnectionInfo connectInfo, String key, String scanCursor, ScanParams scanParams);

    Long hset(RedisConnectionInfo connectInfo, String key, String field, String value);

    Long hsetnx(RedisConnectionInfo connectInfo, String key, String field, String value);

    /**
     * return the number of fields deleted
     * @param connectInfo
     * @param key
     * @param fields
     * @return
     */
    Long hdel(RedisConnectionInfo connectInfo, String key, String... fields);

    /**
     * return value set of key
     * @param connectInfo
     * @param key
     * @return
     */
    List<String> hvals(RedisConnectionInfo connectInfo, String key);
}