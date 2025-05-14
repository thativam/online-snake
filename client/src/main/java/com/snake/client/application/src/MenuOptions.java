package com.snake.client.application.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.snake.client.domain.aplication.Score;

public class MenuOptions extends JPanel {
    final static String imageBasePath = "src/main/java/com/snake/client/resources/gameImages/";

    public MenuOptions() {
        setBackground(Color.DARK_GRAY);
    }

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
        g.drawString("SEARCH MATCH", panelWidth/2 - 80, 210);

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SETTINGS", panelWidth/2 - 50, 310);

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("QUIT", panelWidth/2 - 25, 410);

    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(230, 700);
    }
}
