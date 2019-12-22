package org.algorithm;

/**
 * 雪花算法
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-12-22
 * @since x.y.z
 */
public class SnowFlake {

    /**
     * 起始的时间戳：这个时间戳随意设置，比如执行代码时的时间戳
     */
    private static final long START_STAMP = 1576994077545L;

    /**
     * 每一部分占用的位数
     */
    private static final long SEQUENCE_BIT = 12;   // 序列号占用的位数
    private static final long MACHINE_BIT = 5;     // 机器标识占用的位数
    private static final long DATA_CENTER_BIT = 5; // 数据中心占用的位数

    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT); // aka 31

    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT); // aka 31

    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT); // aka 4095

    /**
     * 机器标识较序列号的偏移量
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;

    /**
     * 数据中心较机器标识的偏移量
     */
    private static final long DATA_CENTER_LEFT = MACHINE_LEFT + MACHINE_BIT;

    /**
     * 时间戳较数据中心的偏移量
     */
    private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private static long dataCenterId;    // 数据中心
    private static long machineId;       // 机器标识
    private static long sequence = 0L;   // 序列号
    private static long lastStamp = -1L; // 上一次时间戳

    /**
     * 产生下一个ID
     */
    public static synchronized long nextId() {
        /* 获取当前时间戳 */
        long currStamp = getNewStamp();

        /* 如果当前时间戳小于上次时间戳则抛出异常 */
        if (currStamp < lastStamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }
        /* 相同毫秒内 */
        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                /* 获取下一时间的时间戳并赋值给当前时间戳 */
                currStamp = getNextStamp();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        /* 当前时间戳存档记录，用于下次产生id时对比是否为相同时间戳 */
        lastStamp = currStamp;

        return (currStamp - START_STAMP) << TIMESTAMP_LEFT                         // 时间戳部分
                | (dataCenterId & MAX_DATA_CENTER_NUM) << DATA_CENTER_LEFT         // 数据中心部分
                | (machineId & MAX_MACHINE_NUM) << MACHINE_LEFT                    // 机器标识部分
                | sequence;                                                        // 序列号部分
    }

    private static long getNextStamp() {
        long nextStamp;
        while ((nextStamp = getNewStamp()) <= lastStamp) {
            System.out.println("Spin wait for next timestamp.");
        }
        return nextStamp;
    }

    private static long getNewStamp() {
        return System.currentTimeMillis();
    }

}
