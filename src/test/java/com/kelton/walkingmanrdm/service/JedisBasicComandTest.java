package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.Map;
import java.util.Set;


/**
 * @Author kelton
 * @Date 2024/4/21 11:48
 * @Version 1.0
 */
public class JedisBasicComandTest {


    static RedisConnectionInfo redisConnectionInfo;

    @BeforeEach
    public void initConnectionInfo() {
        redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.host("127.0.0.1").port(6379).password("0754zzk");
    }


    @Test
    public void getInfo() {
        Map<String, Object> info = RedisBasicCommand.INSTANT.getInfo(redisConnectionInfo);
        System.out.println(info);
    }

    @Test
    public void getCpuInfo() {
        Map<String, Object> cpuInfo = RedisBasicCommand.INSTANT.getCpuInfo(redisConnectionInfo);
        System.out.println(cpuInfo);
    }

    @Test
    public void getMemoryInfo() {
        Map<String, Object> memoryInfo = RedisBasicCommand.INSTANT.getMemoryInfo(redisConnectionInfo);
        System.out.println(memoryInfo);
    }

    @Test
    public void getClientInfo() {
        Map<String, Object> memoryInfo = RedisBasicCommand.INSTANT.getClientInfo(redisConnectionInfo);
        System.out.println(memoryInfo);
    }

    @Test
    public void getStatInfo() {
        Map<String, Object> memoryInfo = RedisBasicCommand.INSTANT.getStatInfo(redisConnectionInfo);
        System.out.println(memoryInfo);
    }

    @Test
    public void getKeySpace() {
        Map<String, Object> memoryInfo = RedisBasicCommand.INSTANT.getKeySpace(redisConnectionInfo);
        System.out.println(memoryInfo);
    }

    @Test
    public void getServerInfo() {
        Map<String, Object> memoryInfo = RedisBasicCommand.INSTANT.getServerInfo(redisConnectionInfo);
        System.out.println(memoryInfo);
    }

    @Test
    public void dbSize() {
        Long dbSize = RedisBasicCommand.INSTANT.dbSize(redisConnectionInfo);
        System.out.println(dbSize);
    }

    @Test
    public void keys() {
        Set<String> keys = RedisBasicCommand.INSTANT.keys(redisConnectionInfo, "*");
        System.out.println(keys);
    }

    @Test
    public void ttl() {
        Long ttl = RedisBasicCommand.INSTANT.ttl(redisConnectionInfo, "aaaaaaaaaaa");
        System.out.println(ttl);
    }

    @Test
    public void scan() {
        String scanPointerStart = ScanParams.SCAN_POINTER_START;
        String scanMatchPattern = "*";
        int count = 2;
        ScanParams scanParams = new ScanParams().match(scanMatchPattern).count(count);
        boolean done = false;
        int scanCount = 1;
        while (!done) {
            ScanResult<String> scanResult = RedisBasicCommand.INSTANT.scan(redisConnectionInfo, scanPointerStart, scanParams);
            scanPointerStart = scanResult.getCursor(); // 获取下一次迭代的游标
            System.out.printf("**********scan %d***************\n", scanCount);
            for (String key : scanResult.getResult()) {
                System.out.println(key); // 处理返回的每一个key
            }
            // 判断是否结束循环
            if (scanPointerStart.equals(ScanParams.SCAN_POINTER_START)) {
                done = true;
            }
            scanCount++;
        }
    }

    @Test
    public void rename() {
        String captchaEnabled = RedisBasicCommand.INSTANT.rename(redisConnectionInfo, "sys_config:sys.account.captchaEnabled", "captchaEnabled");
        System.out.println(captchaEnabled);
    }

    @Test
    public void expireAndTtl() throws InterruptedException {
        String key = "captchaEnabled";
        Long expire = RedisBasicCommand.INSTANT.expire(redisConnectionInfo, key, 60);
        System.out.println(expire);
        Thread.sleep(5000);
        Long ttl = RedisBasicCommand.INSTANT.ttl(redisConnectionInfo, key);
        System.out.println(ttl);
    }

    @Test
    public void type() {
        String key = "sys_dict:sys_oper_type";
        String type = RedisBasicCommand.INSTANT.type(redisConnectionInfo, key);
        System.out.println(type);
    }

    @Test
    public void ping() {
        String ping = RedisBasicCommand.INSTANT.ping(redisConnectionInfo);
        System.out.println(ping);
    }


}
