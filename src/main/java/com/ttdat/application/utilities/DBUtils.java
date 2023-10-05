package com.ttdat.application.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String hostName = "localhost";
    private static final String dbName = "pharmacy";
    private static final String userName = "root";
    private static final String password = "123456";
    /* jdbc:mysql://hostname:port/dbname */
    private static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

    public static Connection openConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection(connectionURL, userName, password);
    }
}
