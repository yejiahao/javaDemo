package org.yejh.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
    public static void main(String[] args) {
        JedisPoolConfig jpc = new JedisPoolConfig();
        try (JedisPool jp = new JedisPool(jpc, "192.168.2.196", 6379); Jedis jedis = jp.getResource()) {
            // Jedis jedis = new Jedis("192.168.2.196", 6379);
            jedis.set("name", "yejiahao");
            System.out.println(jedis.get("name"));
            jedis.append("name", " is a student.");
            System.out.println(jedis.get("name"));
            jedis.del("name");
            System.out.println(jedis.get("name"));
            jedis.mset("id", "20111003457", "name", "yjh", "QQ", "454696086");
            jedis.incr("QQ");
            System.out.println(jedis.get("id") + " " + jedis.get("name") + " " + jedis.get("QQ"));
        }
    }
}
