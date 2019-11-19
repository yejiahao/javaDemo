package org.yejh;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * guava版布隆过滤器
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-11-13
 * @since 1.0.0
 */
public class BloomFilterTest {

    private static int total = 1_000_000;
    @SuppressWarnings("UnstableApiUsage")
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total);

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }
        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            if (!bf.mightContain(i)) {
                System.out.println("判断错误: " + i);
            }
        }
        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bf.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量: " + count);
    }
}
