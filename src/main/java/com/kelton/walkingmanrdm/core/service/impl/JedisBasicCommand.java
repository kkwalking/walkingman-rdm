package com.kelton.walkingmanrdm.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.RedisBasicCommand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JedisBasicCommand implements RedisBasicCommand {

    // 用于获取Jedis连接实例，后续考虑加入连接池
    private Jedis getConnection(RedisConnectionInfo connectionInfo) {
        Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port());
        if (StrUtil.isNotBlank(connectionInfo.password())) {
            jedis.auth(connectionInfo.password());
        }
        return jedis;
    }

    @Override
    public boolean testConnect(RedisConnectionInfo connectionInfo) {
        try (Jedis jedis = new Jedis(connectionInfo.host(), connectionInfo.port())) {
            if (StrUtil.isNotBlank(connectionInfo.password())) {
                jedis.auth(connectionInfo.password());
            }
            if("PONG".equals(jedis.ping())) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
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
    public ScanResult<String> scan(RedisConnectionInfo connectionInfo, String cursor, ScanParams scanParams) {
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
     *
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

    @Override
    public String sendCommand(RedisConnectionInfo connectionInfo, String command, String... args) {
        StringBuilder sb = new StringBuilder();
        try (Jedis jedis = getConnection(connectionInfo)) {
            ProtocolCommand protocolCommand = Protocol.Command.valueOf(command.toUpperCase());

            try {
                Object response = jedis.sendCommand(protocolCommand, args);
                sb.append(decodeResponse(response));
            } catch (Exception e) {
                sb.append("(error) ").append(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("(error) ERROR unknown command ");
            sb.append("`").append(command).append("`, with args beginning with:");
            for (int i = 1; i < args.length; i++) {
                sb.append("`").append(args[i]).append("`,");
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    private String decodeResponse(Object response) {
        if (response instanceof byte[]) {
            return new String((byte[]) response, StandardCharsets.UTF_8);

        } else if (response instanceof List) {
            List<String> ret = ((List<?>) response).stream()
                    .map(this::decodeResponse)
                    .toList();
            StringBuilder retSb = new StringBuilder();
            for (int i = 0; i < ret.size(); i++) {
                retSb.append(i + 1).append(") \"").append(ret.get(i)).append("\"\n");
            }
            return retSb.toString();

        } else if (response instanceof Map) {
            return ((Map<?, ?>) response).entrySet().stream()
                    .map(entry -> decodeResponse(entry.getKey()) + ": " + decodeResponse(entry.getValue()))
                    .collect(Collectors.joining(", "));

        } else if (response instanceof String || response instanceof Long) {

            return String.valueOf(response);

        } else {
            return Arrays.toString((byte[]) response);
        }
    }

}