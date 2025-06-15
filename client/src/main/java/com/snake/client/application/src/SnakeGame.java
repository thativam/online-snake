package com.snake.client.application.src;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class SnakeGame {
    final static String imageBasePath = "src/main/java/com/snake/client/resources/gameImages/";

    public static JPanel createGamePanel(Gameplay gameplay, InfoPanel infoPanel) {
        TitlePanel titlePanel = new TitlePanel(imageBasePath);


        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(gameplay, BorderLayout.CENTER);
        contentPane.add(infoPanel, BorderLayout.EAST);

        return contentPane;
    }
}