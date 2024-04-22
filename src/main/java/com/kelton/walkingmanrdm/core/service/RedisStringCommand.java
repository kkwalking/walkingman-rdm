package com.kelton.walkingmanrdm.core.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisStringComand;

/**
 * @Author zhouzekun
 * @Date 2024/4/22 15:13
 */
public interface RedisStringCommand {

    RedisStringCommand INSTANT = new JedisStringComand();

    String set(RedisConnectionInfo connectInfo, String key, String value);

    String get(RedisConnectionInfo connectInfo, String key);

    Long strlen(RedisConnectionInfo connectInfo, String key);

    String setex(RedisConnectionInfo connectInfo, String key, long seconds, String value);
}
