package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.common.util.SqlUtils;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.ConnectionService;
import com.kelton.walkingmanrdm.core.service.impl.JedisConnectionService;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Author kelton
 * @Date 2024/4/20 22:45
 * @Version 1.0
 */
public class ConnectionServiceTest {


    @Test
    public void connect() {

        SqlUtils.init();

        RedisConnectionInfo redisConnectionInfo = new RedisConnectionInfo();
        redisConnectionInfo.host("127.0.0.1").port(6379).password("0754zzk").database(0).title("本地连接");


        ConnectionService.INSTANT.save(redisConnectionInfo);
        List<RedisConnectionInfo> allConnectList = ConnectionService.INSTANT.getAllConnectList();
        System.out.println(allConnectList);

    }
}
