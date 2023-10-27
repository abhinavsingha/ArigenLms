package com.payroll.erp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnectionConfig {

    private static final String DB_DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_USERNAME = "erpModule";
    private static final String DB_PASSWORD = "erpModule123=";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";

    public static Connection getConnection() {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", DB_USERNAME);
        connectionProps.put("password", DB_PASSWORD);
        //                Class.forName(connectionProps.getProperty(DB_DRIVER_CLASS));
        try {
            conn = DriverManager.getConnection(DB_URL, connectionProps);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to database");
        return conn;
    }

    public static Connection getConnection1() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con =
                    DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:orcl", DB_USERNAME, DB_PASSWORD);
            System.out.println("databaseConnection===============" + con.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("databaseConnection===============" + e.toString());
        }

        return con;
    }
}
