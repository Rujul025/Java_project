package com.example.travelplanner.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.travelplanner.model.Option;

public class OptionDAO {

    public List<Option> getTransportOptions(double maxCost) {
        List<Option> list = new ArrayList<>();
        String sql = "SELECT id, name, type, cost FROM transportation_options WHERE cost <= ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, maxCost);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getString("name"), rs.getString("type"), rs.getDouble("cost")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Option> getHotelOptions(double maxCost) {
        List<Option> list = new ArrayList<>();
        String sql = "SELECT id, name, location, cost_per_night FROM hotel_options WHERE cost_per_night <= ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, maxCost);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Option(rs.getInt("id"), rs.getString("name"), rs.getString("location"), rs.getDouble("cost_per_night")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveUserPreferences(int tripId, int transportId, int hotelId) {
        String query = "INSERT INTO user_trip_preferences (trip_id, transport_option_id, hotel_option_id) VALUES (?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tripId);
            stmt.setInt(2, transportId);
            stmt.setInt(3, hotelId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

