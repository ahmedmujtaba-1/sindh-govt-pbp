package com.sindhgov.pbp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static final String URL = System.getProperty("db.url", "jdbc:h2:~/sindhgov-pbp");
    private static final String USER = System.getProperty("db.user", "sa");
    private static final String PASSWORD = System.getProperty("db.password", "");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
