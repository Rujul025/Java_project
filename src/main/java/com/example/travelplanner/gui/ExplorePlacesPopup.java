package com.example.travelplanner.gui;

import com.example.travelplanner.model.Place;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ExplorePlacesPopup extends JDialog {

    private List<Place> allPlaces;
    private JPanel placesPanel;

    public ExplorePlacesPopup() {
        setTitle("Popular Places to Visit");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout());

        allPlaces = loadPopularPlaces();

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setMargin(new Insets(10, 10, 10, 10));
        searchField.setToolTipText("Search places...");

        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String keyword = searchField.getText().toLowerCase();
                filterPlaces(keyword);
            }
        });

        placesPanel = new JPanel();
        placesPanel.setLayout(new BoxLayout(placesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(placesPanel);

        add(searchField, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        displayPlaces(allPlaces);

        setVisible(true);
    }

    private void displayPlaces(List<Place> places) {
        placesPanel.removeAll();
        for (Place place : places) {
            JPanel card = new JPanel(new GridLayout(0, 1));
            card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.setBackground(new Color(240, 248, 255));

            card.add(new JLabel("🏞️ Name: " + place.getName()));
            card.add(new JLabel("📍 Location: " + place.getCity() + ", " + place.getState() + " (" + place.getZone() + ")"));
            card.add(new JLabel("📚 Type: " + place.getType() + " | Established: " + place.getEstablishmentYear()));
            card.add(new JLabel("🗓️ Best Time to Visit: " + place.getBestTimeToVisit() + " | Weekly Off: " + place.getWeeklyOff()));
            card.add(new JLabel("⭐ Rating: " + place.getGoogleRating() + " (" + place.getNumReviews() + " lakh reviews)"));
            card.add(new JLabel("💸 Fee: ₹" + place.getEntranceFee() + " | ✈️ Near Airport: " + (place.isAirportNearby() ? "Yes" : "No") + " | 📷 DSLR Allowed: " + (place.isDslrAllowed() ? "Yes" : "No")));
            card.add(new JLabel("🧭 Significance: " + place.getSignificance()));

            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
            placesPanel.add(card);
            placesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        placesPanel.revalidate();
        placesPanel.repaint();
    }

    private void filterPlaces(String keyword) {
        List<Place> filtered = new ArrayList<>();
        for (Place place : allPlaces) {
            if (place.getName().toLowerCase().contains(keyword) ||
                    place.getCity().toLowerCase().contains(keyword) ||
                    place.getType().toLowerCase().contains(keyword)) {
                filtered.add(place);
            }
        }
        displayPlaces(filtered);
    }

    private List<Place> loadPopularPlaces() {
        List<Place> places = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/travel_planner";
        String username = "root";
        String password = "rujul";

        String query = "SELECT `Name`, `City`, `State`, `Zone`, `Type`, `Establishment Year`, " +
                "`Best Time to visit`, `Significance`, `Google review rating`, " +
                "`Entrance Fee in INR`, `Airport with 50km Radius`, `Weekly Off`, " +
                "`DSLR Allowed`, `Number of google review in lakhs` FROM places";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Place place = new Place(
                        rs.getString("Name"),
                        rs.getString("City"),
                        rs.getString("State"),
                        rs.getString("Zone"),
                        rs.getString("Type"),
                        rs.getInt("Establishment Year"),
                        rs.getString("Best Time to visit"),
                        rs.getString("Significance"),
                        rs.getDouble("Google review rating"),
                        rs.getInt("Entrance Fee in INR"),
                        rs.getBoolean("Airport with 50km Radius"),
                        rs.getString("Weekly Off"),
                        rs.getBoolean("DSLR Allowed"),
                        rs.getDouble("Number of google review in lakhs")
                );
                places.add(place);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load places from database.",
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return places;
    }
}
