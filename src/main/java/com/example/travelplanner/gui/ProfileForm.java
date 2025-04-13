package com.example.travelplanner.gui;

import com.example.travelplanner.dao.DBHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProfileForm extends JFrame {

    public ProfileForm(int userId) {
        setTitle("Profile");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Color scheme
        Color background = new Color(34, 40, 49);
        Color textColor = Color.WHITE;
        Color accent = new Color(0, 173, 181);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(background);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Heading
        JLabel heading = new JLabel("Your Profile");
        heading.setForeground(accent);
        heading.setFont(new Font("SansSerif", Font.BOLD, 22));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(heading);
        panel.add(Box.createVerticalStrut(20));

        // ====== Fetch user details ======
        String full_name = "N/A";
        String email = "N/A";

        try (Connection conn = DBHelper.getConnection()) {
            String query = "SELECT full_name, email FROM users WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                full_name = rs.getString("full_name");
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel nameLabel = new JLabel("Full Name: " + full_name);
        JLabel emailLabel = new JLabel("Email: " + email);

        for (JLabel label : new JLabel[]{nameLabel, emailLabel}) {
            label.setForeground(textColor);
            label.setFont(new Font("SansSerif", Font.PLAIN, 14));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
            panel.add(Box.createVerticalStrut(10));
        }

        // ====== Trip heading ======
        JLabel tripHeading = new JLabel("Your Trips");
        tripHeading.setForeground(accent);
        tripHeading.setFont(new Font("SansSerif", Font.BOLD, 18));
        tripHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(tripHeading);
        panel.add(Box.createVerticalStrut(10));

        // ====== Fetch trip details ======
        DefaultListModel<String> tripModel = new DefaultListModel<>();

        try (Connection conn = DBHelper.getConnection()) {
            String tripQuery = "SELECT trip_name, start_date, end_date, budget FROM trips WHERE user_id = ?";
            PreparedStatement pst = conn.prepareStatement(tripQuery);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String tripName = rs.getString("trip_name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                double budget = rs.getDouble("budget");

                String tripDetails = String.format("<html><b>%s</b><br>From: %s | To: %s<br>Budget: ₹%.2f</html>",
                        tripName, startDate.toString(), endDate.toString(), budget);
                tripModel.addElement(tripDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trip List
        JList<String> tripList = new JList<>(tripModel);
        tripList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tripList.setBackground(background);
        tripList.setForeground(textColor);
        tripList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                label.setForeground(textColor);
                label.setBackground(isSelected ? accent.darker() : background);
                label.setFont(new Font("SansSerif", Font.PLAIN, 13));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tripList);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        scrollPane.setBorder(BorderFactory.createLineBorder(accent));

        panel.add(scrollPane);
        add(panel);
    }
}
