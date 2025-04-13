package com.example.travelplanner.gui;

import com.example.travelplanner.dao.HotelDAO;
import com.example.travelplanner.model.Hotel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class tripOptionsForm extends JFrame {
    private JPanel hotelDisplayPanel;
    private JTextField cityField, roomTypeField, minPriceField, maxPriceField;
    private JComboBox<String> starRatingBox;
    private final int tripId;
    private double totalBudget;
    private final int userId;

    public tripOptionsForm(int userId, int tripId, double totalBudget) {
        this.userId = userId;
        this.tripId = tripId;
        this.totalBudget = totalBudget;

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Explore Hotels");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Filter panel (left)
        JPanel filterPanel = createFilterPanel();

        // Hotel display panel (right)
        hotelDisplayPanel = new JPanel();
        hotelDisplayPanel.setLayout(new BoxLayout(hotelDisplayPanel, BoxLayout.Y_AXIS));
        hotelDisplayPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(hotelDisplayPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(filterPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // Initially load all hotels
        applyFilter();
        setVisible(true);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, getHeight()));
        panel.setBackground(new Color(34, 40, 49));
        panel.setLayout(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        Color textColor = Color.WHITE;

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setForeground(textColor);
        cityField = new JTextField();

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setForeground(textColor);
        roomTypeField = new JTextField();

        JLabel starLabel = new JLabel("Star Rating:");
        starLabel.setForeground(textColor);
        starRatingBox = new JComboBox<>(new String[]{"", "1", "2", "3", "4", "5"});

        JLabel priceLabel = new JLabel("Price Range:");
        priceLabel.setForeground(textColor);
        minPriceField = new JTextField();
        maxPriceField = new JTextField();

        JButton filterButton = new JButton("Apply Filter");
        filterButton.setBackground(new Color(0, 173, 181));
        filterButton.setForeground(Color.BLACK);
        filterButton.setFont(font);
        filterButton.addActionListener(e -> applyFilter());

        // Add components
        panel.add(cityLabel); panel.add(cityField);
        panel.add(roomTypeLabel); panel.add(roomTypeField);
        panel.add(starLabel); panel.add(starRatingBox);
        panel.add(priceLabel); panel.add(minPriceField); panel.add(maxPriceField);
        panel.add(filterButton);

        return panel;
    }

    private void applyFilter() {
        hotelDisplayPanel.removeAll();

        String city = cityField.getText().trim();
        String roomType = roomTypeField.getText().trim();
        String starRating = starRatingBox.getSelectedItem() != null ? starRatingBox.getSelectedItem().toString() : "";

        double minPrice = 0, maxPrice = Double.MAX_VALUE;
        try {
            if (!minPriceField.getText().isEmpty()) {
                minPrice = Double.parseDouble(minPriceField.getText());
            }
            if (!maxPriceField.getText().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceField.getText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for price range.");
            return;
        }

        HotelDAO dao = new HotelDAO();
        List<Hotel> hotels = dao.filterHotels(city, roomType, starRating, minPrice, maxPrice);

        if (hotels.isEmpty()) {
            JLabel noResults = new JLabel("No hotels found matching your filters.");
            noResults.setFont(new Font("Segoe UI", Font.BOLD, 16));
            noResults.setHorizontalAlignment(SwingConstants.CENTER);
            hotelDisplayPanel.add(noResults);
        } else {
            for (Hotel hotel : hotels) {
                hotelDisplayPanel.add(createHotelCard(hotel));
            }
        }

        hotelDisplayPanel.revalidate();
        hotelDisplayPanel.repaint();
    }

    private JPanel createHotelCard(Hotel hotel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(new Color(245, 245, 245));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        JLabel title = new JLabel("<html><b>" + hotel.getPropertyName() + "</b> (" + hotel.getHotelStarRating() + "★)</html>");
        JLabel location = new JLabel("City: " + hotel.getCity() + " | Area: " + hotel.getArea());
        JLabel desc = new JLabel("<html>" + hotel.getHotelDescription() + "</html>");
        JLabel price = new JLabel("Starting Price: ₹" + hotel.getPrice());

        JButton savePrefButton = new JButton("Save Preference");
        savePrefButton.setBackground(new Color(0, 173, 181));
        savePrefButton.setForeground(Color.BLACK);
        savePrefButton.addActionListener(e -> {
            HotelDAO dao = new HotelDAO();
            boolean success = dao.saveUserHotelPreference(userId, tripId, hotel.getPropertyId());
            if (success) {
                JOptionPane.showMessageDialog(this, "Hotel preference saved!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save preference.");
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.add(title);
        infoPanel.add(location);
        infoPanel.add(desc);
        infoPanel.add(price);
        infoPanel.add(savePrefButton);

        card.add(infoPanel, BorderLayout.CENTER);
        return card;
    }
}
