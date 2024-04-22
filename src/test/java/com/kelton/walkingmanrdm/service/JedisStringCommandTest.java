package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisStringComand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @Author zhouzekun
 * @Date 2024/4/22 15:19
 */
public class JedisStringCommandTest {
    private RedisConnectionInfo connectInfo;

    @BeforeEach

    public void setUp() {
        // 初始化 Redis 连接信息
        connectInfo = new RedisConnectionInfo()
                .host("127.0.0.1")
                .port(6379)
                .password("0754zzk");
    }
    @Test
    public void testSet() {
        JedisStringComand.INSTANT.set(connectInfo, "kelton", "bruce");
    }

    @Test
    public void testGet() {
        String value = JedisStringComand.INSTANT.get(connectInfo, "kelton");
        System.out.println(value);
    }
    @Test
    public void testStrlen() {
        long len = JedisStringComand.INSTANT.strlen(connectInfo, "kelton");
        System.out.println(len);
    }
    @Test
    public void setex() {
        String setex = JedisStringComand.INSTANT.setex(connectInfo, "kelton", 5, "android");
        System.out.println(setex);
    }
}
