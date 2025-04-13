package com.example.travelplanner.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardForm extends JFrame {

    public DashboardForm(int userId) {
        setTitle("Travel Planner - Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Full screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Background Image
        URL bgUrl = getClass().getResource("/icons/backimage1.jpg");
        ImageIcon bgIcon = new ImageIcon(bgUrl);
        Image bgImage = bgIcon.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImage));
        backgroundLabel.setBounds(0, 0, screenWidth, screenHeight);

        // Layered Pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(screenSize);

        // Glass Panel
        int panelWidth = (int) (screenWidth * 0.85);
        int panelHeight = (int) (screenHeight * 0.75);

        JPanel glassPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 120)); // Semi-transparent
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();
            }
        };
        glassPanel.setBounds((screenWidth - panelWidth) / 2, (screenHeight - panelHeight) / 2, panelWidth, panelHeight);
        glassPanel.setOpaque(false);
        glassPanel.setLayout(new BorderLayout());
        glassPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        JLabel headingLabel = new JLabel("Travel Planner Dashboard");
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headingLabel.setForeground(Color.WHITE);

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        topRightPanel.setOpaque(false);

        JButton profileBtn = createRoundIconButton("/icons/Profile.jpeg", "Profile", new Color(0, 173, 181));
        JButton logoutBtn = createRoundIconButton("/icons/log.jpg", "Logout", new Color(255, 87, 87));

        profileBtn.addActionListener(e -> new ProfileForm(userId).setVisible(true));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });

        topRightPanel.add(profileBtn);
        topRightPanel.add(logoutBtn);

        topBar.add(headingLabel, BorderLayout.WEST);
        topBar.add(topRightPanel, BorderLayout.EAST);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome back, traveler!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);

        JLabel dateLabel = new JLabel("Today's Date: " +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        dateLabel.setForeground(new Color(220, 220, 220));

        JLabel quoteLabel = new JLabel("\"Adventure awaits — let's plan something great!\"");
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quoteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        quoteLabel.setForeground(new Color(0, 200, 255));

        JButton createTripBtn = new JButton("➕  Create New Trip");
        createTripBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        createTripBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        createTripBtn.setBackground(new Color(0, 173, 181));
        createTripBtn.setForeground(Color.WHITE);
        createTripBtn.setFocusPainted(false);
        createTripBtn.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        createTripBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createTripBtn.addActionListener(e -> new CreateTripForm(userId).setVisible(true));

        JButton exploreBtn = new JButton("🌍  Explore Popular Places");
        exploreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exploreBtn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        exploreBtn.setBackground(new Color(255, 153, 51));  // Orange-ish
        exploreBtn.setForeground(Color.WHITE);
        exploreBtn.setFocusPainted(false);
        exploreBtn.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        exploreBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exploreBtn.addActionListener(e -> new ExplorePlacesPopup());

        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(exploreBtn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(dateLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(quoteLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(createTripBtn);

        // Add to Glass Panel
        glassPanel.add(topBar, BorderLayout.NORTH);
        glassPanel.add(centerPanel, BorderLayout.CENTER);

        // Add to Layered Pane
        layeredPane.add(backgroundLabel, Integer.valueOf(0));
        layeredPane.add(glassPanel, Integer.valueOf(1));

        add(layeredPane);
        setVisible(true);
    }

    // Round Button Creator
    private JButton createRoundIconButton(String iconPath, String tooltip, Color bgColor) {
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImg = icon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);

        JButton button = new JButton(icon) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(getModel().isRollover() ? bgColor.darker() : bgColor);
                g.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                g.setColor(bgColor.darker());
                g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
            }

            @Override
            public boolean contains(int x, int y) {
                Ellipse2D shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
                return shape.contains(x, y);
            }
        };

        button.setToolTipText(tooltip);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(50, 50));
        return button;
    }
}
