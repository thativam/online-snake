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
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.snake.client.domain.aplication.Apple;
import com.snake.client.domain.aplication.Score;
import com.snake.client.domain.aplication.Snake;
import com.snake.client.domain.aplication.Snake.Direction;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private static final int GAME_WIDTH = 535; // 505 + 30 for borders
    private static final int GAME_HEIGHT = 550; // 501 + 49 for borders

    private List<SubscriberData> subscribers = new ArrayList<>();
    private Score score;

    Direction startingDirection = Direction.LEFT;
    private static int delay = 60;
    private static int playerId = 2;
    Snake[] snakes = new Snake[4];
    Apple[] apples = new Apple[10];

    final static String imageBasePath = "client/src/main/java/com/snake/client/resources/gameImages/" + "";

    private Timer timer;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    private ImageIcon appleImage;
    private ImageIcon snakeBody;
    private ImageIcon snakeHead;

    public Gameplay(Score score) {
        /*
         * for(int i = 0; i < snakes.length ;i++){
         * snakes[i] = new Snake(Direction.RIGHT, i*180+199, 253);
         * }
         */
        setBackground(Color.DARK_GRAY);
        for (int i = 0; i < snakes.length; i++) {
            int xPos = i * 180 + 199;
            int yPos;
            if (snakes.length == 2) {
                yPos = 319;
            } else if (snakes.length == 3) {
                yPos = (i == 2) ? 409 : 229;
                if (i == 2)
                    xPos = 289;
            } else {
                yPos = (i <= 1) ? 229 : 409;
                xPos = (i % 2 == 0) ? 199 : 379;
            }
            if (snakes.length != 4) {
                snakes[i] = new Snake(i, i, xPos, yPos);
            } else {
                snakes[i] = new Snake(i % 2, i % 2, xPos, yPos);
            }
        }
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        g.fillRect(25, 72, 505, 500);

        for (int i = 0; i < snakes.length; i++) {
            if (snakes[i].getMoves() == 0) {
                snakes[i].start();
            }
            snakes[i].paintSnake(this, g, snakeHead, snakeBody);
        }

        for (int i = 0; i < apples.length; i++) {
            if (apples[i].getappleXPos() == snakes[playerId].getSnakexLength()[0]
                    && (apples[i].getappleYPos() == snakes[playerId].getSnakeyLength()[0])) {
                snakes[playerId].increaseSnakeSize();
                notifySubscribers(GameEvents.INCREASE_SCORE);
                apples[i] = new Apple();
            }
        }

        // check if player snake has hit any part of another snake
        for (int j = 0; j < snakes.length; j++) {
            final int snakeId = j;
            AtomicBoolean flag = new AtomicBoolean(true);
            IntStream.range(0, snakes[snakeId].getLengthOfSnake()).parallel().forEach(i -> {
                if (snakes[snakeId].getSnakexLength()[i] == snakes[playerId].getSnakexLength()[0] &&
                        snakes[snakeId].getSnakeyLength()[i] == snakes[playerId].getSnakeyLength()[0]
                        && snakeId != playerId) {
                    flag.set(false);
                }
            });

            if (!flag.get()) {
                snakes[playerId].dead();
                break;
            }
        }

        if (snakes[playerId].getMoves() != 0) {
            for (int i = 0; i < apples.length; i++) {
                appleImage.paintIcon(this, g, apples[i].getappleXPos(), apples[i].getappleYPos());
            }
        }

        if (snakes[playerId].getMoves() == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        // protocols made when the snake dies
        if (snakes[playerId].isDeath()) {
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
        if (snakes[playerId].getMoves() > 0 && !snakes[playerId].isDeath()) {
            snakes[playerId].setCurrentDirection();
            switch (snakes[playerId].getCurrentDirection()) {
                case Direction.RIGHT:
                    snakes[playerId].movementRight();
                    repaint();
                    break;
                case Direction.LEFT:
                    snakes[playerId].movementLeft();
                    repaint();
                    break;
                case Direction.UP:
                    snakes[playerId].movementUp();
                    repaint();
                    break;
                case Direction.DOWN:
                    snakes[playerId].movementDown();
                    repaint();
                    break;
                case Direction.NONE:
                    break;
            }
            snakes[playerId].increaseMoves();
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
                if (snakes[playerId].getMoves() == 0) {
                    snakes[playerId].restart();
                    timer.start();
                }
                if (snakes[playerId].isDeath()) {
                    notifySubscribers(GameEvents.RESTART);
                    snakes[playerId].reset();
                    repaint();
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                snakes[playerId].moveRight();
                repaint();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                snakes[playerId].moveLeft();
                repaint();
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                snakes[playerId].moveUp();
                repaint();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                snakes[playerId].moveDown();
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