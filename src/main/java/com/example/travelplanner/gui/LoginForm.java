package com.example.travelplanner.gui;

import com.example.travelplanner.dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JPanel loginPanel;
    private JCheckBox showPassword;

    public LoginForm() {
        setTitle("Login - Travel Planner");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/icons/backimage.jpg"));
        URL imageUrl = getClass().getResource("/icons/backimage.jpg");
        System.out.println("Loaded image: " + imageUrl);
        Image bgImage = bgIcon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, screenSize.width, screenSize.height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        loginPanel = new RoundedPanel(30);
        loginPanel.setBackground(new Color(34, 40, 49, 220)); // Semi-transparent
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBounds(screenSize.width / 2 - 250, screenSize.height / 2 - 200, 500, 400);
        addLoginComponents();

        layeredPane.add(loginPanel, Integer.valueOf(1));
        add(layeredPane);
        setVisible(true);
    }

    private void addLoginComponents() {
        Color fieldColor = new Color(57, 62, 70);
        Color textColor = Color.WHITE;
        Color accentColor = new Color(0, 173, 181);
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome to Travel Planner");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(accentColor);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(title, gbc);

        gbc.gridwidth = 1;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(textColor);
        emailLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        styleField(emailField, fieldColor, textColor, font);
        gbc.gridx = 1;
        loginPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        styleField(passwordField, fieldColor, textColor, font);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        showPassword = new JCheckBox("Show Password");
        showPassword.setBackground(new Color(34, 40, 49));
        showPassword.setForeground(textColor);
        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(showPassword, gbc);

        showPassword.addActionListener(e -> {
            passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•');
        });

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        styleButton(loginButton, accentColor);
        styleButton(registerButton, accentColor);

        gbc.gridx = 0;
        gbc.gridy = 4;
        loginPanel.add(loginButton, gbc);
        gbc.gridx = 1;
        loginPanel.add(registerButton, gbc);

        // DAO and login logic
        UserDAO userDAO = new UserDAO();

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = userDAO.loginUser(email, pass);
            if (userId != -1) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new DashboardForm(userId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            dispose();
        });
    }

    private void styleField(JTextField field, Color bg, Color fg, Font font) {
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(fg);
        field.setFont(font);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    private void styleButton(JButton button, Color bg) {
        button.setBackground(bg);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    class RoundedPanel extends JPanel {
        private int radius;

        public RoundedPanel(int radius) {
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
