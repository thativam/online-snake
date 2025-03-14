package src;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Font;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    Snake snake = new Snake();

    Apple apple = new Apple();

    private ImageIcon snakeHead;

    private Timer timer;
    private int delay = 100;
    private ImageIcon snakeBody;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    private int snakeHeadXPos = 379;

    private ImageIcon appleImage;

    private Random random = new Random();

    private ImageIcon titleImage;

    Score score = new Score();

    private String highScore;

    private ImageIcon arrowImage;
    private ImageIcon shiftImage;

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (snake.moves == 0) {
            for (int i = 0; i < 5; i++) {
                snake.snakexLength[i] = snakeHeadXPos;
                snakeHeadXPos -= 6;
                snake.snakeyLength[i] = 355;
            }
        }

        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 506, 501);

        g.setColor(Color.black);
        g.fillRect(25, 72, 505, 500);

        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        g.drawRect(653, 130, 221, 1);

        score.sortHighScore();
        highScore = score.getHighScore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 705, 200);

        g.drawRect(653, 490, 221, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 15));
        g.drawString("Posição Maçã: " + apple.printPosition(), 600, 530);
        g.drawString("Posição cobra: X: " + snake.snakexLength[0] + " Y: "+ snake.snakeyLength[0], 600, 570);
       

        arrowImage = new ImageIcon("images/keyboardArrow.png");
        arrowImage.paintIcon(this, g, 670, 590);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 770, 590);

        shiftImage = new ImageIcon("images/shift.png");
        shiftImage.paintIcon(this, g, 695, 625);
        g.drawString("Boost", 770, 640);

        snakeHead = new ImageIcon("images/snakeHead4.png");
        snakeHead.paintIcon(this, g, snake.snakexLength[0], snake.snakeyLength[0]);

        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (i == 0 && (snake.right || snake.left || snake.up || snake.down)) {
                snakeHead = new ImageIcon("images/snakeHead4.png");
                snakeHead.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody = new ImageIcon("images/snakeimage4.png");
                snakeBody.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
        }

        appleImage = new ImageIcon("images/apple4.png");

        if (apple.getappleXPos() == snake.snakexLength[0] && (apple.getappleYPos() == snake.snakeyLength[0])) {
            snake.lengthOfSnake++;
            score.increaseScore();
            apple = new Apple();
        }

        if (snake.moves != 0) {
            appleImage.paintIcon(this, g, apple.getappleXPos(), apple.getappleYPos());
        }

        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 26));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
        }

        // if snake's head's x and y positions == any other part of the snake, the snake dies 
        for (int i = 1; i < snake.lengthOfSnake; i++) {
            if (snake.snakexLength[i] == snake.snakexLength[0] && snake.snakeyLength[i] == snake.snakeyLength[0]) {
                snake.dead();
            }
        }

        // protocols made when the snake dies
        if (snake.death) {
            score.saveNewScore();

            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 190, 340);

            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 18));
            g.drawString("Your Score : " + score.getScore(), 250, 370);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 187, 400);
        }
        g.dispose();
    }

    public void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (snake.right) {
            snake.movementRight();
            repaint();
        }
        if (snake.left) {
            snake.movementLeft();
            repaint();
        }
        if (snake.up) {
            snake.movementUp();
            repaint();
        }
        if (snake.down) {
            snake.movementDown();
            repaint();
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
                        timer.setDelay(10);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (snake.moves == 0) {
                    snake.moves++;
                    snake.right = true;
                }
                if (snake.death) {
                    snake.moves = 0;
                    snake.lengthOfSnake = 5;
                    score.resetScore();
                    repaint();
                    snake.death = false;
                }
                break;
            case KeyEvent.VK_RIGHT:
                snake.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                snake.moveLeft();
                break;
            case KeyEvent.VK_UP:
                snake.moveUp();
                break;
            case KeyEvent.VK_DOWN:
                snake.moveDown();
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