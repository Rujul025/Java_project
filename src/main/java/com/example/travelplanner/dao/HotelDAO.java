package com.example.travelplanner.dao;

import com.example.travelplanner.model.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {
    private final String URL = "jdbc:mysql://localhost:3306/travel_planner";
    private final String USER = "root";
    private final String PASSWORD = "rujul";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Hotel> filterHotels(String city, String roomType, String starRating, double minPrice, double maxPrice) {
        List<Hotel> hotels = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM hotels WHERE 1=1");

        if (!city.isEmpty()) query.append(" AND city LIKE ?");
        if (!roomType.isEmpty()) query.append(" AND room_type LIKE ?");
        if (!starRating.isEmpty()) query.append(" AND hotel_star_rating = ?");
        query.append(" AND price BETWEEN ? AND ?");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int index = 1;
            if (!city.isEmpty()) stmt.setString(index++, "%" + city + "%");
            if (!roomType.isEmpty()) stmt.setString(index++, "%" + roomType + "%");
            if (!starRating.isEmpty()) stmt.setString(index++, starRating);
            stmt.setDouble(index++, minPrice);
            stmt.setDouble(index++, maxPrice);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setPropertyId(rs.getString("property_id"));
                hotel.setPropertyName(rs.getString("property_name"));
                hotel.setCity(rs.getString("city"));
                hotel.setArea(rs.getString("area"));
                hotel.setAddress(rs.getString("address"));
                hotel.setRoomType(rs.getString("room_type"));
                hotel.setHotelStarRating(rs.getString("hotel_star_rating"));
                hotel.setHotelDescription(rs.getString("hotel_description"));
                hotel.setSiteStayReviewRating(rs.getString("site_stay_review_rating"));
                hotel.setHotelFacilities(rs.getString("hotel_facilities"));
                hotel.setPrice(rs.getDouble("price")); // ✅ Set price

                hotels.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotels;
    }

    public boolean saveUserHotelPreference(int userId, int tripId, String hotelId) {
        String sql = "INSERT INTO user_trip_preferences (id, trip_id, hotel_id) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, tripId);
            stmt.setString(3, hotelId);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
