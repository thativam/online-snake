package com.snake.client.application.src;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.snake.client.domain.ServerSubscriber;
import com.snake.client.domain.aplication.Score;

public class SnakeGame {
    final static String imageBasePath = "client/src/main/java/com/snake/client/resources/gameImages/";

    public static void main(String[] args) {
        initializeGame(null); // This is just for testing the UI, doesnt need a server.
    }

    public static void initializeGame(ServerSubscriber serverSubscriber) {
        JFrame obj = new JFrame();
        Score score = new Score();
        Gameplay gameplay = new Gameplay(score);
        TitlePanel titlePanel = new TitlePanel(imageBasePath);
        InfoPanel infoPanel = new InfoPanel(score);
        gameplay.subscribe(infoPanel);
        gameplay.serverSubscribe(serverSubscriber);

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setResizable(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(gameplay, BorderLayout.CENTER);
        contentPane.add(infoPanel, BorderLayout.EAST);

        // Set content pane and size
        obj.setContentPane(contentPane);
        obj.setSize(910, 750); // Set size before positioning

        // Position and show
        // obj.setLocationRelativeTo(null); // Center window
        obj.setVisible(true); // Make visible LAST
    }
}