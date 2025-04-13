package com.example.travelplanner.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TripDAO {

    public int createTrip(int userId, String tripName, String startDate, String endDate, double budget,
                          String destination, int numTravelers, double transportBudget) {
        String query = "INSERT INTO trips (user_id, trip_name, start_date, end_date, budget, destination, num_travelers, transport_budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userId);
            stmt.setString(2, tripName);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setDouble(5, budget);
            stmt.setString(6, destination);
            stmt.setInt(7, numTravelers);
            stmt.setDouble(8, transportBudget);

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
