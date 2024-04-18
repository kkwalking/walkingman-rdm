package com.kelton.kkwalkingrdm.common;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 数据管理模块
 * @Author kelton
 * @Date 2024/4/18 23:16
 * @Version 1.0
 */
@Slf4j
public class DataManager {

    private static String driver = "org.apache.derby.iapi.jdbc.InternalDriver";
    private static String protocol = "jdbc:derby:";
    String dbName = "E:\\temp\\derby\\db";

    static {
        try {
            log.info("begin to init DataManager");
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataFromDerby() {
        try {
            Connection conn = DriverManager.getConnection(protocol + dbName + ";create=true");
            Statement statement = conn.createStatement();
            // statement.execute("create table t_user(id int primary key, name varchar(20))");
            ResultSet resultSet = statement.executeQuery("select * from t_user");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
            }
            conn.close();
            statement.close();
            resultSet.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DataManager derbyTest = new DataManager();
        derbyTest.getDataFromDerby();
    }
}
