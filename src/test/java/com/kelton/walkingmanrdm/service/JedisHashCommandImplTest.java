package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import com.kelton.walkingmanrdm.core.service.RedisHashCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jedis Hash Service Test
 */
public class JedisHashCommandImplTest {
    private RedisConnectionInfo connectionInfo;

    private String key;

    @BeforeEach
    public void setUp() {
        // 初始化 Redis 连接信息
        connectionInfo = new RedisConnectionInfo()
            .host("127.0.0.1")
            .port(6379)
            .password("0754zzk");

        key = "address";
    }

    @Test
    public void testHget() {
        String value = RedisHashCommand.INSTANT.hget(connectionInfo, key, "street");
        assertNotNull(value);
        System.out.println(value);
    }

    @Test
    public void testHgetall() {
        Map<String, String> hashEntries = RedisHashCommand.INSTANT.hgetall(connectionInfo, key);
        assertNotNull(hashEntries);
        assertFalse(hashEntries.isEmpty());
    }

    @Test
    public void testHkeys() {
        List<String> fields = RedisHashCommand.INSTANT.hkeys(connectionInfo, key);
        assertNotNull(fields);
        assertFalse(fields.isEmpty());
    }

    @Test
    public void testHlen() {
        Long length = RedisHashCommand.INSTANT.hlen(connectionInfo, key);
        assertNotNull(length);
        assertTrue(length > 0);
    }

    @Test
    public void testHmset() {
        Map<String, String> hashEntries = new HashMap<>();
        hashEntries.put("city", "gz");
        hashEntries.put("province", "gd");
        String status = RedisHashCommand.INSTANT.hmset(connectionInfo, key, hashEntries);
        assertEquals("OK", status);
    }

    @Test
    public void testHscan() {
        String scanPointerStart = ScanParams.SCAN_POINTER_START;
        String scanMatchPattern = "*";
        int count = 2;
        ScanParams scanParams = new ScanParams().match(scanMatchPattern).count(count);
        boolean done = false;
        int scanCount = 1;
        while (!done) {
            ScanResult<Map.Entry<String, String>> scanResult = RedisHashCommand.INSTANT.hscan(connectionInfo, key, scanPointerStart, scanParams);
            scanPointerStart = scanResult.getCursor(); // 获取下一次迭代的游标
            System.out.printf("**********scan %d***************\n", scanCount);
            scanResult.getResult().forEach(System.out::println);
            // 判断是否结束循环
            if (scanPointerStart.equals(ScanParams.SCAN_POINTER_START)) {
                done = true;
            }
            scanCount++;
        }
    }

    @Test
    public void testHset() {
        Long result = RedisHashCommand.INSTANT.hset(connectionInfo, key, "zone", "py");
        System.out.println(result);
    }

    @Test
    public void testHsetnx() {
        Long result = RedisHashCommand.INSTANT.hsetnx(connectionInfo, key, "company", "dd");
        System.out.println(result);
    }

    @Test
    public void testHdel() {
        Long result = RedisHashCommand.INSTANT.hdel(connectionInfo, key, "company", "zone");
        System.out.println(result);
    }

    @Test
    public void testHvals() {
        List<String> values = RedisHashCommand.INSTANT.hvals(connectionInfo, key);
        System.out.println(values);
    }
}