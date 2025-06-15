// Em MainMenu.java

package com.snake.client.application.src;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class MainMenu {
    final static String imageBasePath = "src/main/java/com/snake/client/resources/gameImages/";
    public static JPanel createMainMenuPanel(Runnable onSearchMatchAction) {
        TitlePanel titlePanel = new TitlePanel(imageBasePath);
        MenuOptions menuOptions = new MenuOptions(onSearchMatchAction);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.add(titlePanel, BorderLayout.NORTH);
        contentPane.add(menuOptions, BorderLayout.CENTER);

        return contentPane;
    }
}