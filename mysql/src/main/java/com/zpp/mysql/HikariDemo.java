package com.zpp.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author pingpingZhong
 *         Date: 2017/9/16
 *         Time: 15:25
 */
public class HikariDemo {


    public static void main(String[] args)throws  Exception {
        HikariConfig config = new HikariConfig("/application.properties");
        HikariDataSource ds = new HikariDataSource(config);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        while(true) {
            try{
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement("select 1");
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            System.out.println(metaData.getColumnName(1));
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                if(resultSet != null){
                    resultSet.close();
                }
                if(preparedStatement != null){
                    preparedStatement.close();
                }
                if(connection != null){
                    connection.close();
                }
            }

        }



    }



}
