package org.yejh.jdbc;

import java.sql.*;

public class JDBCUtil {
    private JDBCUtil() {
        throw new AssertionError();
    }

    public static void main(String[] args) throws SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springmvcdemo?useSSL=false&characterEncoding=utf-8&serverTimezone=GMT%2B8", "root", "20170419");
             PreparedStatement pstat = conn.prepareStatement("SELECT uid AS uu, email AS ue, account AS ua FROM user");
             ResultSet rs = pstat.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.printf("%s\t%s\t%s\n", metaData.getColumnName(1), metaData.getColumnName(2), metaData.getColumnLabel(3));
            System.out.println("------------------------");
            while (rs.next()) {
                System.out.printf("%s\t%s\t%s\n", rs.getString(1), rs.getString(2), rs.getString("ua"));
            }
        }

    }
}
