package org.yejh;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * try-with-resource 语法糖分析
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2021-01-07
 * @since x.y.z
 */
public class TWRTest {

    private static void method3() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://vm0.yejh.cn:3307/mysql?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "20170419")) {
            System.out.println("m3");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("unlock");
        }
    }

    private static void method3TWR() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://vm0.yejh.cn:3307/mysql?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "20170419");
            Throwable var1 = null;
            try {
                int i = 1 / 0;
                System.out.println("m3");
            } catch (Throwable var19) {
                var1 = var19;
                throw var19;
            } finally {
                if (conn != null) {
                    if (var1 != null) {
                        try {
                            int i = 1 / 0;
                            conn.close();
                        } catch (Throwable var18) {
                            var1.addSuppressed(var18);
                        }
                    } else {
                        int i = 1 / 0;
                        conn.close();
                    }
                }
            }
        } catch (Exception var21) {
            var21.printStackTrace();
        } finally {
            System.out.println("unlock");
        }
    }

}
