package com.snake.client.application.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.snake.client.application.src.Snake.Direction;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private static int delay = 60;
    private int snakeHeadXPos = 379;

    Snake snake = new Snake(Direction.RIGHT);
    Snake snake2 = new Snake(Direction.UP);
    Score score = new Score();
    
    private String highScore;
    final static String imageBasePath = "client\\src\\main\\java\\com\\snake\\client\\resources\\gameImages\\" + ""; 
    Apple[] apples = new Apple[5];


    private Timer timer;
    private boolean started = false;

    AtomicBoolean speedUp = new AtomicBoolean(true);

    private ImageIcon appleImage;
    private ImageIcon titleImage;
    private ImageIcon arrowImage;
    private ImageIcon shiftImage;
    private ImageIcon snakeBody;
    private ImageIcon snakeHead;


    public Gameplay() {
        for(int i = 0; i < apples.length; i++) {
            apples[i] = new Apple(); // Criando cada instância
        }
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        loadImages();
        timer = new Timer(delay, this);
        
    }

    private void loadImages(){
        snakeHead = new ImageIcon(imageBasePath + "snakeHead4.png");
        snakeBody = new ImageIcon(imageBasePath + "snakeimage4.png");
        appleImage = new ImageIcon(imageBasePath + "apple4.png");
        titleImage = new ImageIcon(imageBasePath + "title.png");
        arrowImage = new ImageIcon(imageBasePath + "keyboardArrow.png");
        shiftImage = new ImageIcon(imageBasePath + "shift.png");
    }

    public void paint(Graphics g) {
        if (snake.moves == 0 && started == false) {
            snakeHeadXPos = snake.start(snakeHeadXPos);
            started = true;
        }


        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        
        titleImage.paintIcon(this, g, 25, 11);

        // Game Border for the game
        g.setColor(Color.WHITE);
        g.drawRect(24, 71, 506, 501);

        // Internal Panel (where the snake is going to move)
        g.setColor(Color.black);
        g.fillRect(25, 72, 505, 500);

        // Game Border for the score
        g.setColor(Color.WHITE);
        g.drawRect(653, 71, 223, 614);

        // Internal Panel (where the score is going to be displayed)
        g.setColor(Color.black);
        g.fillRect(654, 72, 221, 613);

        // Rectangle inside the score panel
        g.setColor(Color.white);
        g.drawRect(653, 130, 221, 1);

        //Set Font Color
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("SCORE : " + score.getScore(), 720, 110);
        highScore = score.getHighscore();
        g.drawString("HIGHSCORE", 705, 180);
        drawString(g, highScore, 755, 200);

        g.drawRect(653, 490, 221, 1);
        g.setFont(new Font("Helvetica", Font.BOLD, 15));
        g.drawString("Posição Maçã: " + apples[0].printPosition(), 665, 510);
        g.drawString("Posição cobra: X: " + snake.snakexLength[0] + " Y: "+ snake.snakeyLength[0], 665, 530);
       

        arrowImage.paintIcon(this, g, 660, 550);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Movement", 770, 580);

        shiftImage.paintIcon(this, g, 685, 625);
        g.drawString("Boost", 770, 640);

        snakeHead.paintIcon(this, g, snake.snakexLength[0], snake.snakeyLength[0]);
        snakeHead.paintIcon(this, g, snake2.snakexLength[0], snake2.snakeyLength[0]);

        for (int i = 0; i < snake.lengthOfSnake; i++) {
            if (i == 0 && (snake.currentDirection != Direction.NONE)) {
                snakeHead.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody.paintIcon(this, g, snake.snakexLength[i], snake.snakeyLength[i]);
            }
        }

        for (int i = 0; i < snake2.lengthOfSnake; i++) {
            if (i == 0) {
                snakeHead.paintIcon(this, g, snake2.snakexLength[i], snake2.snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody.paintIcon(this, g, snake2.snakexLength[i], snake2.snakeyLength[i]);
            }
        }

        for(int i = 0; i < apples.length ;i++){
            if (apples[i].getappleXPos() == snake.snakexLength[0] && (apples[i].getappleYPos() == snake.snakeyLength[0])) {
                snake.lengthOfSnake++;
                score.increaseScore();
                apples[i] = new Apple();
            }
        }

        if (snake.moves != 0) {
            for(int i = 0; i < apples.length; i++) {
                appleImage.paintIcon(this, g, apples[i].getappleXPos(), apples[i].getappleYPos());
            }
        }

        if (snake.moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to Start the Game!", 70, 300);
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
        if (snake.moves > 0 && !snake.death) {
            snake.currentDirection = snake.nextDirection;
            switch (snake.currentDirection) {
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
            snake.moves++;
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
                        timer.setDelay(delay/5);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (snake.moves == 0) {
                    snake.restart();
                    snake2.restart();
                    timer.start();
                }
                if (snake.death) {
                    
                    score.resetScore();
                    snake.reset();
                    snake2.reset();
                    started = false;
                    repaint();
                }
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                System.out.println("Right");
                snake.moveRight();
                
                repaint();
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                snake.moveLeft();
                
                repaint();
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                System.out.println("up");
                snake.moveUp();
                
                repaint();
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                System.out.println("down");
                snake.moveDown();
                repaint();
                break;
        }
        System.out.println(" --_--_--_--");;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            speedUp.set(true);
            timer.setDelay(delay);
        }
    }

}