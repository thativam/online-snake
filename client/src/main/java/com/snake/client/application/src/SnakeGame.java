package com.snake.client.application.src;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class SnakeGame {
    final static String imageBasePath = "client/src/main/java/com/snake/client/resources/gameImages/";

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.getContentPane().setBackground(Color.DARK_GRAY);
        obj.setLayout(new BorderLayout());
        obj.setBounds(10, 10, 910, 750);
        obj.add(new TitlePanel(imageBasePath), BorderLayout.NORTH);
        obj.add(gameplay, BorderLayout.CENTER);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}