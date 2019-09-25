package org.algorithm;

/**
 * https://leetcode-cn.com/problems/climbing-stairs/
 * <p>
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-09-26
 * @since x.y.z
 */
public class ClimbStairs {

    public int climbStairs(int n) {
        for (int i : new int[]{1, 2}) {
            if (n == i) {
                return n;
            }
        }
        return climbStairs(n - 1) + climbStairs(n - 2);
    }
}
