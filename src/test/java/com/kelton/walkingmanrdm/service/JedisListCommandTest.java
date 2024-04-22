package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisListCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JedisListCommandTest {

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
    void testLpushAndLrange() {
        String key = "testList";
        RedisListCommand.INSTANT.lpush(connectInfo, key, "val1", "val2", "val3");
        List<String> result = RedisListCommand.INSTANT.lrange(connectInfo, key, 0, -1);
        assertEquals(Arrays.asList("val3", "val2", "val1"), result);
    }

    @Test
    void testLlen() {
        String key = "testListLength";
        RedisListCommand.INSTANT.lpush(connectInfo, key, "val1");
        long length = RedisListCommand.INSTANT.llen(connectInfo, key);
        assertEquals(1, length);
    }

    @Test
    void testLset() {
        String key = "testListSet";
        RedisListCommand.INSTANT.lpush(connectInfo, key, "val1", "val2");
        RedisListCommand.INSTANT.lset(connectInfo, key, 1, "val2Updated");
        List<String> result = RedisListCommand.INSTANT.lrange(connectInfo, key, 0, -1);
        assertTrue(result.contains("val2Updated"));
    }

    @Test
    void testLpop() {
        String key = "testListPop";
        RedisListCommand.INSTANT.lpush(connectInfo, key, "val1", "val2");
        String poppedValue = RedisListCommand.INSTANT.lpop(connectInfo, key);
        assertEquals("val2", poppedValue);
    }

    @Test
    void testRpushAndRpop() {
        String key = "testListRpushRpop";
        RedisListCommand.INSTANT.rpush(connectInfo, key, "val1", "val2");
        String poppedValue = RedisListCommand.INSTANT.rpop(connectInfo, key);
        assertEquals("val2", poppedValue);
    }
    
    @Test
    void testLrem() {
        String key = "testListLrem";
        RedisListCommand.INSTANT.lpush(connectInfo, key, "val1", "val2", "val3");
        long removedCount = RedisListCommand.INSTANT.lrem(connectInfo, key, 1, "val2");
        assertEquals(1, removedCount);
    }
}