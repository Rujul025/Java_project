package com.example.travelplanner.gui;

import com.example.travelplanner.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class RegisterForm extends JFrame {
    private boolean showPassword = false;

    public RegisterForm() {
        setTitle("Register - Travel Planner");
        setSize(450, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color backgroundColor = new Color(34, 40, 49);
        Color fieldColor = new Color(57, 62, 70);
        Color textColor = Color.WHITE;
        Color accentColor = new Color(0, 173, 181);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Your Account", SwingConstants.CENTER);
        title.setForeground(accentColor);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Full Name
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        nameField.setBackground(fieldColor);
        nameField.setForeground(textColor);
        nameField.setCaretColor(textColor);
        nameField.setToolTipText("Enter your full name");
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        usernameField.setBackground(fieldColor);
        usernameField.setForeground(textColor);
        usernameField.setCaretColor(textColor);
        usernameField.setToolTipText("Enter your username");
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(15);
        emailField.setBackground(fieldColor);
        emailField.setForeground(textColor);
        emailField.setCaretColor(textColor);
        emailField.setToolTipText("Enter a valid email address");
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBackground(fieldColor);
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);
        passwordField.setToolTipText("At least 6 characters, 1 digit, 1 letter");
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Confirm Password
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(textColor);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBackground(fieldColor);
        confirmPasswordField.setForeground(textColor);
        confirmPasswordField.setCaretColor(textColor);
        confirmPasswordField.setToolTipText("Re-enter your password");
        gbc.gridx = 1;
        panel.add(confirmPasswordField, gbc);

        // Show Password Checkbox
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Passwords");
        showPasswordCheckbox.setForeground(textColor);
        showPasswordCheckbox.setBackground(backgroundColor);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(showPasswordCheckbox, gbc);

        showPasswordCheckbox.addActionListener(e -> {
            showPassword = showPasswordCheckbox.isSelected();
            passwordField.setEchoChar(showPassword ? (char) 0 : '•');
            confirmPasswordField.setEchoChar(showPassword ? (char) 0 : '•');
        });

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(accentColor);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel);

        UserDAO userDAO = new UserDAO();

        registerButton.addActionListener(e -> {
            String fullName = nameField.getText().trim();
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isStrongPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 6 characters and include letters and digits.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDAO.registerUser(fullName, username, email, password)) { // Add name to DB if supported
                JOptionPane.showMessageDialog(this, "Registration successful!");
                new LoginForm().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isStrongPassword(String password) {
        return password.length() >= 6 &&
                password.matches(".*[A-Za-z].*") &&
                password.matches(".*\\d.*");
    }
}
