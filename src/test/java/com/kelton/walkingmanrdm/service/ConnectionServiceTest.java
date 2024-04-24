package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.core.service.impl.JedisConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Author kelton
 * @Date 2024/4/20 22:45
 * @Version 1.0
 */
public class ConnectionServiceTest {

    @BeforeEach
    public void init() {
        SqlUtils.init();
    }


    @Test
    public void save() {

        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.host("127.0.0.1").port(6379).password("aaa").database(0).title("本地连接");

        ConnectionService.INSTANT.save(redisConnectionInfo);
        List<RedisConnectionInfo> allConnectList = ConnectionService.INSTANT.getAllConnectList();
        System.out.println(allConnectList);

    }

    @Test
    public void update() {
        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.id(1).password("aaa");

        ConnectionService.INSTANT.update(redisConnectionInfo);
        List<RedisConnectionInfo> allConnectList = ConnectionService.INSTANT.getAllConnectList();
        System.out.println(allConnectList);
    }

    @Test
    public void del() {
        ConnectionService.INSTANT.deleteById(1);
        List<RedisConnectionInfo> allConnectList = ConnectionService.INSTANT.getAllConnectList();
        System.out.println(allConnectList);
    }

    @Test
    public void getById() {
        RedisConnectionInfo connectionInfo = ConnectionService.INSTANT.getById(1);
        System.out.println(connectionInfo);
    }

    @Test
    public void getAll() {
        List<RedisConnectionInfo> allConnectList = ConnectionService.INSTANT.getAllConnectList();
        System.out.println(allConnectList);
    }
}
