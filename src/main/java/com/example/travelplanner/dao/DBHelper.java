package com.example.travelplanner.dao;

import java.sql.*;

public class DBHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/travel_planner";
    private static final String USER = "root";  // your MySQL username
    private static final String PASS = "rujul";  // your MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

