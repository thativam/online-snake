package com.snake.client.application.src;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final ImageIcon titleImage;

    public TitlePanel(String imageBasePath) {
        this.titleImage = new ImageIcon(imageBasePath + "title.png");
        setPreferredSize(new Dimension(900, 70));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(24, 10, 852, 55);
        g.fillRect(0, 0, getWidth(), getHeight());
        setBackground(getBackground());
        titleImage.paintIcon(this, g, 25, 11);
    }
}
