package com.kelton.walkingmanrdm.core.service;

import cn.hutool.core.util.StrUtil;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisBasicCommand;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface RedisBasicCommand {

    RedisBasicCommand INSTANT = new JedisBasicCommand();

    public boolean testConnect(RedisConnectionInfo connectionInfo);

    String flushdb(RedisConnectionInfo connectInfo);

    String flushall(RedisConnectionInfo connectInfo);

    ScanResult<String> scan(RedisConnectionInfo connectInfo, String cursor,ScanParams scanParams);

    Set<String> keys(RedisConnectionInfo connectInfo, String pattern);

    Long del(RedisConnectionInfo connectInfo, String key);

    String rename(RedisConnectionInfo connectInfo, String key, String newKey);

    Long expire(RedisConnectionInfo connectInfo, String key, int seconds);

    /**
     * return "none" if key not exist
     * @param connectInfo
     * @param key
     * @return
     */
    String type(RedisConnectionInfo connectInfo, String key);

    /**
     * -1: permanent
     * -2: key not exist
     * @param connectInfo
     * @param key
     * @return
     */
    Long ttl(RedisConnectionInfo connectInfo, String key);

    String ping(RedisConnectionInfo connectInfo);

    Map<String, Object> getInfo(RedisConnectionInfo connectInfo);

    Map<String, Object> getCpuInfo(RedisConnectionInfo connectInfo);

    Map<String, Object> getMemoryInfo(RedisConnectionInfo connectInfo);

    Map<String, Object> getServerInfo(RedisConnectionInfo connectInfo);

    Map<String, Object> getKeySpace(RedisConnectionInfo connectInfo);

    Map<String, Object> getClientInfo(RedisConnectionInfo connectInfo);

    Map<String, Object> getStatInfo(RedisConnectionInfo connectInfo);


    Long dbSize(RedisConnectionInfo connectInfo);

    default Map<String, Object> strToMap(String str) {
        Map<String, Object> result = new HashMap<>();
        for (String s : str.split("\r\n")) {
            if (!StrUtil.startWith(s, "#") && StrUtil.isNotBlank(s)) {
                String[] v = s.split(":");
                if (v.length > 1) {
                    result.put(v[0], v[1]);
                } else {
                    result.put(v[0], "");
                }
            }
        }
        return result;
    }


}
