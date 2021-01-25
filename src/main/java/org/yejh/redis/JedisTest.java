package org.yejh.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class JedisTest {

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws InterruptedException {
        JedisPoolConfig jpc = new JedisPoolConfig();
        try (JedisPool jp = new JedisPool(jpc, "vmx.yejh.cn", 6379, Protocol.DEFAULT_TIMEOUT, "20170419");
             Jedis jedis = jp.getResource()) {
//            Jedis jedis = new Jedis("vmx.yejh.cn");
            jedis.set("name", "yejiahao");
            System.out.println("name: " + jedis.get("name"));
            jedis.append("name", " is a student.");
            System.out.println("name: " + jedis.get("name"));
            jedis.del("name");
            System.out.println("name: " + jedis.get("name"));
            jedis.mset("id", "20111003457", "name", "yjh", "QQ", "12345678");
            jedis.incr("QQ");
            System.out.printf("id: %s, name: %s, QQ: %s\n", jedis.get("id"), jedis.get("name"), jedis.get("QQ"));

            String reqId = UUID.randomUUID().toString();
            System.out.println(tryDistributedLock(jedis, "key", reqId));
            System.out.println(releaseDistributedLock(jedis, "key", reqId));
        }

        // 哨兵模式
        Set<String> sentinels = new HashSet<>();
        sentinels.add("vm2.yejh.cn:26379");
        sentinels.add("vm2.yejh.cn:26380");
        sentinels.add("vm2.yejh.cn:26381");
        try (JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels, "20170419");
             Jedis jedis = pool.getResource()) {
            while (true) {
                jedis.set("abcd", "1234");
                System.out.println("result: " + jedis.get("abcd"));
                TimeUnit.SECONDS.sleep(20L);
            }
        }
    }

    /**
     * 获取分布式锁
     *
     * @param jedis
     * @param key
     * @param reqId
     * @return
     */
    private static boolean tryDistributedLock(Jedis jedis, String key, String reqId) {
        String result = jedis.set(key, reqId, SetParams.setParams().nx().ex(20));
        if (Objects.equals(result, LOCK_SUCCESS)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param jedis
     * @param key
     * @param reqId
     * @return
     */
    private static boolean releaseDistributedLock(Jedis jedis, String key, String reqId) {
        if (Objects.equals(jedis.get(key), reqId)) {
            // 若在此时，这把锁突然不是这个客户端的，则会误解锁
            if (Objects.equals(jedis.del(key), RELEASE_SUCCESS)) {
                return true;
            }
        }
        return false;
    }
}
