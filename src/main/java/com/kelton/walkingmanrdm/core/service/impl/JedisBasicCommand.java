package com.kelton.walkingmanrdm.core.service.impl;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.Map;
import java.util.Set;

public class JedisBasicCommand implements RedisBasicCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        // 根据 connectionInfo 内的详细信息建立连接
        // 这里只是一个示例，您需要根据实际情况来创建和管理 Jedis 实例
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StringUtils.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public String flushdb(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.flushDB();
        }
    }

    @Override
    public String flushall(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.flushAll();
        }
    }

    @Override
    public ScanResult<String> scan(RedisConnectionInfo connectionInfo, String cursor,ScanParams scanParams) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.scan(cursor, scanParams);
        }
    }

    @Override
    public Set<String> keys(RedisConnectionInfo connectionInfo, String pattern) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.keys(pattern);
        }
    }

    @Override
    public Long del(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.del(key);
        }
    }

    @Override
    public String rename(RedisConnectionInfo connectionInfo, String key, String newKey) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.rename(key, newKey);
        }
    }

    @Override
    public Long expire(RedisConnectionInfo connectionInfo, String key, int seconds) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.expire(key, seconds);
        }
    }

    @Override
    public String type(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.type(key);
        }
    }

    @Override
    public Long ttl(RedisConnectionInfo connectionInfo, String key) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.ttl(key);
        }
    }

    /**
     * return "PONG" if connection is in work
     * @param connectionInfo
     * @return
     */
    @Override
    public String ping(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.ping();
        }
    }
    
    // 下面的 getInfo 等方法可以按照类似模式实现，将返回的结果转换为 Map
    @Override
    public Map<String, Object> getInfo(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return strToMap(jedis.info());
        }
    }

    @Override
    public Map<String, Object> getCpuInfo(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("cpu"));
        }

    }

    @Override
    public Map<String, Object> getMemoryInfo(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("memory"));
        }
    }

    @Override
    public Map<String, Object> getServerInfo(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("server"));
        }
    }

    @Override
    public Map<String, Object> getKeySpace(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("keyspace"));
        }
    }

    @Override
    public Map<String, Object> getClientInfo(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("clients"));
        }
    }

    @Override
    public Map<String, Object> getStatInfo(RedisConnectionInfo connectInfo) {
        try (Jedis jedis = getConnection(connectInfo)) {
            return strToMap(jedis.info("stats"));
        }
    }

    @Override
    public Long dbSize(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = getConnection(connectionInfo)) {
            return jedis.dbSize();
        }
    }

}