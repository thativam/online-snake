package com.snake.client.application.src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.snake.client.domain.aplication.Apple;
import com.snake.client.domain.aplication.Score;
import com.snake.client.domain.aplication.Snake;
import com.snake.client.domain.aplication.Snake.Direction;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private static int delay = 60;
    private int snakeHeadXPos = 379;

    private static final int GAME_WIDTH = 535; // 505 + 30 for borders
    private static final int GAME_HEIGHT = 550; // 501 + 49 for borders

    private List<SubscriberData> subscribers = new ArrayList<>();
    Snake snake = new Snake(Direction.RIGHT);
    Snake snake2 = new Snake(Direction.UP);
    private Score score;

    final static String imageBasePath = "client/src/main/java/com/snake/client/resources/gameImages/" + "";
    Apple[] apples = new Apple[5];

    private Timer timer;
    private boolean started = false;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    private ImageIcon appleImage;
    private ImageIcon snakeBody;
    private ImageIcon snakeHead;

    public Gameplay(Score score) {
        for (int i = 0; i < apples.length; i++) {
            apples[i] = new Apple(); // Criando cada instância
        }
        // SET SNAKE BORDER CORRECT "Snake.setBOTTOM_BORDER(22);"
        this.score = score;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        loadImages();
        timer = new Timer(delay, this);

    }

    private void loadImages() {
        snakeHead = new ImageIcon(imageBasePath + "snakeHead4.png");
        snakeBody = new ImageIcon(imageBasePath + "snakeimage4.png");
        appleImage = new ImageIcon(imageBasePath + "apple4.png");
    }

    public void paint(Graphics g) {
        if (snake.getMoves() == 0 && started == false) {
            snakeHeadXPos = snake.start(snakeHeadXPos);
            started = true;
        }
        int gameX = 24;
        int gameY = 71;
        int gameWidth = 505;
        int gameHeight = 501;

        // every thing should be heightened by 55(h-55 probably)
        // Game Border for the game
        g.setColor(Color.WHITE);
        g.drawRect(gameX, gameY, gameWidth, gameHeight);

        // Internal Panel (where the snake is going to move)
        g.setColor(Color.black);
        g.fillRect(gameX + 1, gameY + 1, gameWidth - 1, gameHeight - 1);

        snake.paintSnake(this, g, snakeHead, snakeBody);

        for (int i = 0; i < apples.length; i++) {
            if (apples[i].getappleXPos() == snake.getSnakexLength()[0]
                    && (apples[i].getappleYPos() == snake.getSnakeyLength()[0])) {
                snake.increaseSnakeSize();
                notifySubscribers(GameEvents.INCREASE_SCORE);
                apples[i] = new Apple();
            }
        }

        if (snake.getMoves() != 0) {
            for (int i = 0; i < apples.length; i++) {
                appleImage.paintIcon(this, g, apples[i].getappleXPos(), apples[i].getappleYPos());
            }
        }

        if (snake.getMoves() == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        if (snake.isDeath()) {
            notifySubscribers(GameEvents.SNAKE_DEAD);
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 145, 340);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 220, 370);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 157, 400);
        }

        g.dispose();
    }

    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void subscribe(SubscriberData subscriber) {
        subscribers.add(subscriber);
    }

    private void notifySubscribers(GameEvents evt) {
        for (SubscriberData subscriber : subscribers) {
            subscriber.updateData(evt);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (snake.getMoves() > 0 && !snake.isDeath()) {
            snake.setCurrentDirection();
            switch (snake.getCurrentDirection()) {
                case Direction.RIGHT:
                    snake.movementRight();
                    repaint();
                    break;
                case Direction.LEFT:
                    snake.movementLeft();
                    repaint();
                    break;
                case Direction.UP:
                    snake.movementUp();
                    repaint();
                    break;
                case Direction.DOWN:
                    snake.movementDown();
                    repaint();
                    break;
                case Direction.NONE:
                    break;
            }
            snake.increaseMoves();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SHIFT:
                if (speedUp.compareAndSet(true, false)) {
                    timer.setDelay(delay / 5);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (snake.getMoves() == 0) {
                    snake.restart();
                    snake2.restart();
                    timer.start();
                }
                if (snake.isDeath()) {
                    notifySubscribers(GameEvents.RESTART);
                    score.resetScore();
                    snake.reset();
                    snake2.reset();
                    started = false;
                    repaint();
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                snake.moveRight();
                repaint();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                snake.moveLeft();
                repaint();
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                snake.moveUp();
                repaint();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                snake.moveDown();
                repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }

}