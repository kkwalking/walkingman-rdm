package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisListCommand;

import java.util.List;

public interface RedisListCommand {

    RedisListCommand INSTANT = new JedisListCommand();

    List<String> lrange(RedisConnectionInfo connectInfo, String key, long start, long stop);

    Long lrem(RedisConnectionInfo connectInfo, String key, long count, String value);

    Long llen(RedisConnectionInfo connectInfo, String key);

    String lpop(RedisConnectionInfo connectInfo, String key);

    List<String> lpop(RedisConnectionInfo connectInfo, String key, long count);

    Long lpush(RedisConnectionInfo connectInfo, String key, String... values);

    String lset(RedisConnectionInfo connectInfo, String key, long index, String value);

    String rpop(RedisConnectionInfo connectInfo, String key);

    List<String> rpop(RedisConnectionInfo connectInfo, String key, long count);

    Long rpush(RedisConnectionInfo connectInfo, String key, String... values);

}