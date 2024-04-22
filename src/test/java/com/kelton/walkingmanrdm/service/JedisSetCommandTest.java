package com.kelton.walkingmanrdm.service;

import com.kelton.walkingmanrdm.core.model.RedisConnectionInfo;
import com.kelton.walkingmanrdm.core.service.impl.JedisBasicCommand;
import com.kelton.walkingmanrdm.core.service.impl.JedisSetCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class JedisSetCommandTest {
    private RedisConnectionInfo connectionInfo;

    private JedisSetCommand command;

    private final String baseKey = "testSet";
    @BeforeEach
    public void setup() {
        // 初始化你的连接信息和JedisSetCommand实例
        connectionInfo = new RedisConnectionInfo().host("127.0.0.1").port(6379).password("0754zzk");
        command = new JedisSetCommand();
        // 我们加入初始数据来进行测试
        command.sadd(connectionInfo, baseKey, "member1", "member2", "member3");
    }


    @Test
    public void testSadd() {
        Long result = command.sadd(connectionInfo, baseKey, "member4");
        assertEquals(Long.valueOf(1), result);
    }

    @Test
    public void testScard() {
        Long size = command.scard(connectionInfo, baseKey);
        assertEquals(Long.valueOf(3), size);
    }

    @Test
    public void testSdiff() {
        String otherKey = baseKey + ":other";
        command.sadd(connectionInfo, otherKey, "member1");
        Set<String> diff = command.sdiff(connectionInfo, baseKey, otherKey);

        assertTrue(diff.contains("member2"));
        assertTrue(diff.contains("member3"));
    }

    @Test
    public void testSdiffstore() {
        String otherKey = baseKey + ":other";
        String destinationKey = baseKey + ":dest";
        command.sadd(connectionInfo, otherKey, "member1");
        command.sdiffstore(connectionInfo, destinationKey, baseKey, otherKey);

        Set<String> result = command.smembers(connectionInfo, destinationKey);
        assertTrue(result.contains("member2"));
        assertTrue(result.contains("member3"));

    }

    @Test
    public void testSinter() {
        String otherKey = baseKey + ":other";
        command.sadd(connectionInfo, otherKey, "member1", "member2");
        Set<String> inter = command.sinter(connectionInfo, baseKey, otherKey);

        assertTrue(inter.contains("member1"));
        assertTrue(inter.contains("member2"));
        assertFalse(inter.contains("member3"));

        JedisBasicCommand.INSTANT.del(connectionInfo, otherKey);
    }

    @Test
    public void testSinterstore() {
        String otherKey = baseKey + ":other";
        String destinationKey = baseKey + ":dest";
        command.sadd(connectionInfo, otherKey, "member1", "member2");
        command.sinterstore(connectionInfo, destinationKey, baseKey, otherKey);

        Set<String> result = command.smembers(connectionInfo, destinationKey);
        assertTrue(result.contains("member1"));
        assertTrue(result.contains("member2"));

        JedisBasicCommand.INSTANT.del(connectionInfo, otherKey);
        JedisBasicCommand.INSTANT.del(connectionInfo, destinationKey);
    }

    @Test
    public void testSismember() {
        Boolean isMember = command.sismember(connectionInfo, baseKey, "member1");
        assertTrue(isMember);
    }

    @Test
    public void testSmembers() {
        Set<String> members = command.smembers(connectionInfo, baseKey);

        assertTrue(members.contains("member1"));
        assertTrue(members.contains("member2"));
        assertTrue(members.contains("member3"));
    }

    @Test
    public void testSmismember() {
        List<Boolean> isMembers = command.smismember(connectionInfo, baseKey, "member1", "member4");

        assertTrue(isMembers.get(0));
        assertTrue(isMembers.get(1));
    }

    @Test
    public void testSmove() {
        String destinationKey = baseKey + ":dest";
        command.sadd(connectionInfo, destinationKey, "member1");
        Long result = command.smove(connectionInfo, baseKey, destinationKey, "member2");

        assertEquals(Long.valueOf(1), result);
        Set<String> membersDest = command.smembers(connectionInfo, destinationKey);
        assertTrue(membersDest.contains("member2"));

        JedisBasicCommand.INSTANT.del(connectionInfo, destinationKey);
    }

    @Test
    public void testSpop() {
        String popped = command.spop(connectionInfo, baseKey);
        assertNotNull(popped);

        Set<String> members = command.smembers(connectionInfo, baseKey);
        assertFalse(members.contains(popped));
    }

    @Test
    public void testSpopWithCount() {
        Set<String> popped = command.spop(connectionInfo, baseKey, 2);
        assertEquals(2, popped.size());
    }

    @Test
    public void testSrandmember() {
        String member = command.srandmember(connectionInfo, baseKey);
        assertNotNull(member);
    }

    @Test
    public void testSrandmemberWithCount() {
        List<String> members = command.srandmember(connectionInfo, baseKey, 2);
        assertEquals(2, members.size());
    }

    @Test
    public void testSrem() {
        Long removed = command.srem(connectionInfo, baseKey, "member1", "member2");
        assertEquals(Long.valueOf(2), removed);
    }

    @Test
    public void testSunion() {
        String otherKey = baseKey + ":other";
        command.sadd(connectionInfo, otherKey, "member1", "member4");
        Set<String> union = command.sunion(connectionInfo, baseKey, otherKey);

        assertTrue(union.contains("member1"));
        assertTrue(union.contains("member2"));
        assertTrue(union.contains("member3"));
        assertTrue(union.contains("member4"));

        JedisBasicCommand.INSTANT.del(connectionInfo, otherKey);
    }

    @Test
    public void testSscan() {
        ScanResult<String> scanResult = command.sscan(connectionInfo, baseKey, "0");
        assertNotNull(scanResult);
        assertTrue(scanResult.getResult().size() > 0);
    }

    @Test
    public void testSscanWithCount() {
        ScanResult<String> scanResult = command.sscan(connectionInfo, baseKey, "0", 2);
        assertNotNull(scanResult);
        assertTrue(scanResult.getResult().size() > 0);
    }

    @Test
    public void testSscanWithPattern() {
        ScanResult<String> scanResult = command.sscan(connectionInfo, baseKey, "0", "member?");
        assertNotNull(scanResult);
        assertTrue(scanResult.getResult().size() > 0);
    }
}