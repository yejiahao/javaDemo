package org.yejh.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.*;

public class JedisTest {
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;

    public static void main(String[] args) {
        JedisPoolConfig jpc = new JedisPoolConfig();
        try (JedisPool jp = new JedisPool(jpc, "192.168.201.138", 6379); Jedis jedis = jp.getResource()) {
//            Jedis jedis = new Jedis("192.168.201.138");
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
