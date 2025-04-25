package com.snake.client.application.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.snake.client.domain.GameEvents;
import com.snake.client.domain.SubscriberData;
import com.snake.client.domain.aplication.Score;

public class InfoPanel extends JPanel implements SubscriberData {
    private Score score;
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;
    final static String imageBasePath = "client/src/main/java/com/snake/client/resources/gameImages/";

    public InfoPanel(Score score) {
        this.score = score;
        loadImages();
        setBackground(Color.DARK_GRAY);
    }

    private void loadImages() {
        arrowImage = new ImageIcon(imageBasePath + "keyboardArrow.png");
        shiftImage = new ImageIcon(imageBasePath + "shift.png");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Score Panel Background
        g.setColor(Color.WHITE);
        g.drawRect(5, 5, panelWidth - 10, panelHeight - 10);
        g.setColor(Color.black);
        g.fillRect(6, 6, panelWidth - 11, panelHeight - 11);

        // Score content
        g.setColor(Color.white);
        g.drawLine(5, 60, panelWidth - 5, 60); // Horizontal divider

        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 20, 40);
        String highScore = score.getHighscore();
        g.drawString("HIGHSCORE", 20, 110);
        drawString(g, highScore, 20, 130);

        // Controls section
        g.drawLine(5, 420, panelWidth - 5, 420); // Horizontal divider
        g.setFont(new Font("Helvetica", Font.BOLD, 15));
        g.drawString("Controls:", 20, 450);

        arrowImage.paintIcon(this, g, 15, 470);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 110, 500);

        shiftImage.paintIcon(this, g, 30, 540);
        g.drawString("Boost", 110, 560);
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void updateData(GameEvents event) {
        switch (event) {
            case GameEvents.INCREASE_SCORE:
                score.increaseScore();
                break;
            case GameEvents.SNAKE_DEAD:
            case GameEvents.GAME_OVER:
                score.saveNewScore();
                break;
            case GameEvents.RESTART:
                score.resetScore();
                break;
            default:
                break;
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(230, 700);
    }
}