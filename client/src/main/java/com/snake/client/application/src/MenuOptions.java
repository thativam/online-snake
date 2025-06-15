// Em MenuOptions.java

package com.snake.client.application.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class MenuOptions extends JPanel {
    // ... (suas variáveis existentes)
    private final int SEARCH_MATCH_Y = 210;
    private final int SETTINGS_Y = 310;
    private final int QUIT_Y = 410;
    private final int OPTION_HEIGHT = 30;

    // Adicione um campo para a ação de callback
    private final Runnable onSearchMatchAction;

    // Modifique o construtor para aceitar a ação
    public MenuOptions(Runnable onSearchMatchAction) {
        this.onSearchMatchAction = onSearchMatchAction; // Armazene a ação

        setBackground(Color.DARK_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                int panelWidth = getWidth();
                int searchMatchX = panelWidth / 2 - 80;
                int settingsX = panelWidth / 2 - 50;
                int quitX = panelWidth / 2 - 25;
                int optionWidth = 160;

                if (mouseY >= SEARCH_MATCH_Y - OPTION_HEIGHT + 10 && mouseY <= SEARCH_MATCH_Y + 10 &&
                    mouseX >= searchMatchX && mouseX <= searchMatchX + optionWidth) {
                    onSearchMatchClicked();
                } else if (mouseY >= SETTINGS_Y - OPTION_HEIGHT + 10 && mouseY <= SETTINGS_Y + 10 &&
                           mouseX >= settingsX && mouseX <= settingsX + optionWidth) {
                    onSettingsClicked();
                } else if (mouseY >= QUIT_Y - OPTION_HEIGHT + 10 && mouseY <= QUIT_Y + 10 &&
                           mouseX >= quitX && mouseX <= quitX + optionWidth) {
                    onQuitClicked();
                }
            }
        });
    }

    // ... (paintComponent e outros métodos permanecem os mesmos)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        g.setColor(Color.WHITE);
        g.drawRect(5, 5, panelWidth - 10, panelHeight - 10);
        g.setColor(Color.black);
        g.fillRect(6, 6, panelWidth - 11, panelHeight - 11);

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SEARCH MATCH", panelWidth / 2 - 80, SEARCH_MATCH_Y);

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SETTINGS", panelWidth / 2 - 50, SETTINGS_Y);

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("QUIT", panelWidth / 2 - 25, QUIT_Y);
    }


    private void onSearchMatchClicked() {
        System.out.println("SEARCH MATCH clicked! Triggering screen change...");
        // Execute a ação que foi passada no construtor
        if (onSearchMatchAction != null) {
            onSearchMatchAction.run();
        }
    }

    private void onSettingsClicked() {
        System.out.println("SETTINGS clicked!");
    }

    private void onQuitClicked() {
        System.out.println("QUIT clicked!");
        System.exit(0);
    }
    
    // ... (seus outros métodos como drawString e getPreferredSize)
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(230, 700);
    }
}