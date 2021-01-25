package org.yejh.jdbc;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库查询压测例子
 * <p>
 * SET NAMES utf8mb4;
 * SET FOREIGN_KEY_CHECKS = 0;
 * <p>
 * -- ----------------------------
 * -- Table structure for table_test
 * -- ----------------------------
 * DROP TABLE IF EXISTS `table_test`;
 * CREATE TABLE `table_test`  (
 * `tid` int(11) NOT NULL,
 * `tname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
 * `create_time` datetime(0) NULL DEFAULT NULL,
 * `update_time` datetime(0) NULL DEFAULT NULL,
 * PRIMARY KEY (`tid`) USING BTREE
 * ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
 * <p>
 * -- ----------------------------
 * -- Records of table_test
 * -- ----------------------------
 * INSERT INTO `table_test` VALUES (1, 'aaa', '2020-03-09 14:48:38', '2020-05-26 12:34:56');
 * INSERT INTO `table_test` VALUES (2, 'bbb', '2020-03-09 14:48:38', '2020-03-09 14:48:38');
 * INSERT INTO `table_test` VALUES (3, 'ccc', '2020-03-01 03:04:05', '2020-03-09 14:48:38');
 * <p>
 * SET FOREIGN_KEY_CHECKS = 1;
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2020-06-06
 * @since 1.1.0
 */
public class DBStressTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBStressTest.class);

    private static final int THREAD_NUM = 1000;

    private static final CountDownLatch cdl = new CountDownLatch(1);

    private static final ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
        private final AtomicInteger cnt = new AtomicInteger();

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, "t-" + cnt.incrementAndGet());
        }
    });

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_NUM; i++) {
            es.execute(() -> {
                try {
                    LOGGER.info("wait for cdl");
                    cdl.await();
                    LOGGER.info("begin query");
                    queryDB();
                    LOGGER.info("end query");
                } catch (InterruptedException | SQLException e) {
                    LOGGER.error("exception: {}", e.getMessage(), e);
                }
            });
        }
        LOGGER.info("main start");
        TimeUnit.SECONDS.sleep(3L);
        cdl.countDown();
        es.shutdown();
    }

    private static void queryDB() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://vm2.yejh.cn:3307/mydb?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "20170419");
             PreparedStatement pStat = conn.prepareStatement("SELECT tid, tname, create_time AS gmt_create, update_time FROM `table_test`");
             ResultSet rs = pStat.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            LOGGER.info("{}  {}  {}  {}", metaData.getColumnName(1), metaData.getColumnName(2), metaData.getColumnLabel(3), metaData.getColumnLabel(4));
            LOGGER.info("-------------------------------------");
            while (rs.next()) {
                LOGGER.info("{}  {}  {}  {}", rs.getString("tid"), rs.getString(2), rs.getString("gmt_create"), rs.getString(4));
            }
        }
    }

}
