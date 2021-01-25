package org.yejh.redis;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * RedissonSample
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2021-01-22
 * @since 1.1.0
 */
public class RedissonSample {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setTimeout(5000).setAddress("redis://vm3.yejh.cn:6379").setPassword("20170419");
        RedissonClient client = Redisson.create(config);
        RBucket<Object> key = client.getBucket("key");
        key.set("val", 20, TimeUnit.SECONDS);
        System.out.println(key.get());
    }
}
