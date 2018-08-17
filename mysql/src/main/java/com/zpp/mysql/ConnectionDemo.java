package com.zpp.mysql;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.naming.Reference;
import javax.sql.PooledConnection;
import java.sql.*;

/**
 * @author pingpingZhong
 *         Date: 2017/7/12
 *         Time: 15:13
 */

public class ConnectionDemo {

    private static String url = "jdbc:mysql://mysql/push_envelope?user=pe&password=push-envelope&&serverTimezone=GMT&useUnicode=yes&characterEncoding=utf-8&useSSL=false&autoReconnect=true&autoReconnectForPools=true&failOverReadOnly=false&maxReconnects=10";
    private static PooledConnection pooledConnection = null;

    static {
        try {
            MysqlConnectionPoolDataSource mysqlDataSource = new MysqlConnectionPoolDataSource();
            mysqlDataSource.setURL(url);
            //Reference reference = new Reference("");
            //mysqlDataSource.setPropertiesViaRef(reference);
            pooledConnection = mysqlDataSource.getPooledConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return pooledConnection.getConnection();
    }


    public static void main(String[] args) throws Exception {
        System.out.println("-------------使用连接池----------------");
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            //开始时间
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                Thread.currentThread().sleep(3000);
                conn = getConnection();
                ps = conn.prepareStatement("select 1");
                rs = ps.executeQuery();
                while (rs.next()) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            }
            //结束时间
        }


        long endTime = System.currentTimeMillis();
        System.out.println("execute time:" + (endTime - beginTime));

        System.out.println("------------不使用连接池-----------------");

        long beginTime1 = System.currentTimeMillis();
        for (int i = 0; i < 2000; i++) {
            //开始时间
            Connection conn = DriverManager.getConnection(url, "pe", "push-envelope");
            try {
                PreparedStatement ps = conn.prepareStatement("select 1");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.close();
            }
            //结束时间
        }
        long endTime1 = System.currentTimeMillis();

        System.out.println("execute time:" + (endTime1 - beginTime1));
    }
}
