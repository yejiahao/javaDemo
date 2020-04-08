package org.yejh.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * guava 版布隆过滤器
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2020-04-08
 * @since 28.2-jre
 */
@SuppressWarnings("UnstableApiUsage")
public class BloomFilterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BloomFilterTest.class);

    private static int total = 1_000_000;
    private static double fpp = 0.01D;

    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total, fpp);

    public static void main(String[] args) {
        LOGGER.info("初始化数据到过滤器中");
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }
        LOGGER.info("匹配已在过滤器中的值，是否有匹配不上的");
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                LOGGER.warn("判断错误: {}", i);
            }
        }
        LOGGER.info("匹配不在过滤器中的 10000 个值，有多少匹配出来");
        int count = 0;
        for (int i = total, size = total + 10_000; i < size; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }
        LOGGER.info("误判的数量: {}", count);
    }
}
