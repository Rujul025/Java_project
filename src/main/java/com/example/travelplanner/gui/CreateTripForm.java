package com.example.travelplanner.gui;

import com.example.travelplanner.dao.TripDAO;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTripForm extends JFrame {

    private final Color bgColor = new Color(34, 40, 49);
    private final Color fieldColor = new Color(57, 62, 70);
    private final Color textColor = Color.WHITE;
    private final Color accentColor = new Color(0, 173, 181);
    private final Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
    private final Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

    public CreateTripForm(int userId) {
        setTitle("Create Trip - Travel Planner");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // UI Components
        JTextField nameField = createField();
        JTextField destinationField = createField();
        JSpinner startSpinner = createDateSpinner();
        JSpinner endSpinner = createDateSpinner();
        JTextField budgetField = createField();
        JSpinner travelersSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JTextField transportField = createField();

        JButton createBtn = new JButton("Create Trip");
        createBtn.setBackground(accentColor);
        createBtn.setForeground(Color.BLACK);
        createBtn.setFont(labelFont);
        createBtn.setFocusPainted(false);

        // Layout Components
        int y = 0;
        addRow(panel, gbc, y++, "Trip Name:", nameField);
        addRow(panel, gbc, y++, "Destination:", destinationField);
        addRow(panel, gbc, y++, "Start Date:", startSpinner);
        addRow(panel, gbc, y++, "End Date:", endSpinner);
        addRow(panel, gbc, y++, "Total Budget:", budgetField);
        addRow(panel, gbc, y++, "No. of Travelers:", travelersSpinner);
        addRow(panel, gbc, y++, "Transportation Budget:", transportField);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(createBtn, gbc);

        add(panel);

        // Button Logic
        createBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String destination = destinationField.getText().trim();
            Date startDate = (Date) startSpinner.getValue();
            Date endDate = (Date) endSpinner.getValue();
            int travelers = (int) travelersSpinner.getValue();

            String start = new SimpleDateFormat("yyyy-MM-dd").format(startDate);
            String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate);

            double budget, transportBudget;
            try {
                budget = Double.parseDouble(budgetField.getText().trim());
                transportBudget = Double.parseDouble(transportField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for budgets.");
                return;
            }

            if (name.isEmpty() || destination.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Trip name and destination cannot be empty.");
                return;
            }

            if (endDate.before(startDate)) {
                JOptionPane.showMessageDialog(this, "End date cannot be before start date.");
                return;
            }

            TripDAO tripDAO = new TripDAO();
            int tripId = tripDAO.createTrip(userId, name, start, end, budget, destination, travelers, transportBudget);

            if (tripId != -1) {
                JOptionPane.showMessageDialog(this, "Trip created!");

                // Launch hotel filter + hotel selection form
                new tripOptionsForm(userId, tripId, budget); // Only passing total budget since no transport now

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error creating trip.");
            }
        });

        setVisible(true);
    }

    // Helper: Create consistent styled JTextField
    private JTextField createField() {
        JTextField tf = new JTextField();
        tf.setBackground(fieldColor);
        tf.setForeground(textColor);
        tf.setCaretColor(textColor);
        tf.setFont(fieldFont);
        tf.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        tf.setPreferredSize(new Dimension(250, 35)); // Ensures it stretches nicely
        return tf;
    }

    // Helper: Create date spinner
    private JSpinner createDateSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        spinner.setPreferredSize(new Dimension(250, 35)); // Match text field size
        return spinner;
    }

    // Helper: Add a labeled row to the panel
    private void addRow(JPanel panel, GridBagConstraints gbc, int y, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(textColor);
        label.setFont(labelFont);

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
}
