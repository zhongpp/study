package com.zpp.mysql;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author pingpingZhong
 *         Date: 2017/8/8
 *         Time: 15:03
 */
public class JdbcTest {

    private static String url = "jdbc:mysql://mysql/push_envelope?user=pe&password=push-envelope&&serverTimezone=GMT&useUnicode=yes&characterEncoding=utf-8&useSSL=false&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";

    public static void main(String[] args) throws Exception {
        MysqlConnectionPoolDataSource mysqlDataSource = new MysqlConnectionPoolDataSource();
        mysqlDataSource.setURL(url);
        PooledConnection pooledConnection = mysqlDataSource.getPooledConnection();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        conn = pooledConnection.getConnection();
        stmt = conn.createStatement();
        String sql = "show session variables like '%timeout'";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out
                    .println(rs.getString(1) + ":  " + rs.getString(2));
        }
        Thread.currentThread().sleep(21000);
        sql = "select 1";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out
                    .println(rs.getInt(1));
        }
    }

}
