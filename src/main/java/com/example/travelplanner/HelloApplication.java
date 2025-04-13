package com.example.travelplanner;

import javax.swing.*;

public class HelloApplication extends JFrame {
    public HelloApplication() {
        setTitle("Test Frame");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HelloApplication();
    }
}
