package com.snake.client.application.src;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu {
    final static String imageBasePath = "src/main/java/com/snake/client/resources/gameImages/";

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        TitlePanel titlePanel = new TitlePanel(imageBasePath);
        MenuOptions MenuOptions = new MenuOptions();

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setResizable(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(MenuOptions, BorderLayout.CENTER);

        // Set content pane and size
        obj.setContentPane(contentPane);
        obj.setSize(910, 750); // Set size before positioning

        // Position and show
        // obj.setLocationRelativeTo(null); // Center window
        obj.setVisible(true); // Make visible LAST
    }
}