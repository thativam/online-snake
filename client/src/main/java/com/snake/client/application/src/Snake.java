package com.snake.client.application.src;

import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Snake {

    enum Direction {
        RIGHT, LEFT, UP, DOWN, NONE;

        public boolean isOpposite(Direction direction) {
            return this == LEFT && direction == RIGHT || this == RIGHT && direction == LEFT
                    || this == UP && direction == DOWN || this == DOWN && direction == UP;
        }
    }
    Direction startingDirection;
    Direction currentDirection;
    Direction nextDirection;
    int startingHeadPositionX;
    int startingHeadPositionY;
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];
    static int snakeInitialSize = 5;
    int lengthOfSnake;
    int moves;
    final static int SPEED = 6;
    boolean death;

    public Snake(int currentDirectionInt, int startingDirectionInt, int startingHeadPositionX, int startingHeadPositionY) {
        this.startingHeadPositionX = startingHeadPositionX;
        this.startingHeadPositionY = startingHeadPositionY;
        this.currentDirection = Direction.values()[currentDirectionInt];
        this.nextDirection = Direction.values()[currentDirectionInt];
        this.startingDirection = Direction.values()[startingDirectionInt];
        this.death=false;
        this.lengthOfSnake=5;
        this.moves=0;
    }

    public void paintSnake(JPanel gameFrame, Graphics g, ImageIcon snakeHead, ImageIcon snakeBody) {        
        snakeHead.paintIcon(gameFrame, g, this.snakexLength[0], this.snakeyLength[0]);
        IntStream.range(1, this.lengthOfSnake).parallel().forEach(i -> 
            snakeBody.paintIcon(gameFrame, g, this.snakexLength[i], this.snakeyLength[i])
        );
    }
    public Boolean generalCheck(){
        // if snake's head's x and y positions == any other part of the snake, the snake dies
        AtomicBoolean flag = new AtomicBoolean(true);
        IntStream.range(1, this.lengthOfSnake).parallel().forEach(i -> {
            if (this.snakexLength[i] == this.snakexLength[0] && this.snakeyLength[i] == this.snakeyLength[0]) {
                flag.set(false);
            }
        });
        return flag.get();
    }

    public void start(){
        for (int i = 0; i < snakeInitialSize; i++) {
            if(startingDirection == Direction.LEFT)
                this.snakexLength[i] = this.startingHeadPositionX + (i * SPEED);
            else
                this.snakexLength[i] = this.startingHeadPositionX - (i * SPEED);
            this.snakeyLength[i] = this.startingHeadPositionY; 
        }
    }

    public void moveRight(){
        if (this.moves != 0 && !this.death) {
            if (!currentDirection.isOpposite(Direction.RIGHT)) {
                nextDirection = Direction.RIGHT;
            }
        }
    }

    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            if (!currentDirection.isOpposite(Direction.LEFT)) {
                nextDirection = Direction.LEFT;
            }
        }
    }

    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            if (!currentDirection.isOpposite(Direction.UP)) {
                nextDirection = Direction.UP;
            } 
        }
    }

    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            if (!currentDirection.isOpposite(Direction.DOWN)) {
                nextDirection = Direction.DOWN;
            }
        }
    }

    public void dead() {
        this.death = true;
        this.currentDirection = Direction.NONE;
    }

    private int[] updateArray(int[] arr){
        int[] temp = new int[750];

        IntStream.range(1, lengthOfSnake + 1)
        .parallel()
        .forEach(i -> temp[i] = arr[i - 1]);
        
        temp[0] = arr[0];
        return temp;
    }

    public void movementRight(){
        if (!generalCheck()){
            dead();
        }
        if (this.moves != 0 && !this.death) {
            this.snakeyLength = updateArray(snakeyLength);
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakexLength[i] = this.snakexLength[i] + SPEED;
                } else {
                    this.snakexLength[i] = this.snakexLength[i - 1];
                }
                if (this.snakexLength[0] > 524) {
                    this.snakexLength[0] -= SPEED;
                    dead();
                }
            }
        }
    }

    public void movementLeft(){
        if (!generalCheck()){
            dead();
        }
        if (this.moves != 0 && !this.death) {
            this.snakeyLength = updateArray(snakeyLength);
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakexLength[i] = this.snakexLength[i] - SPEED;
                } else {
                    this.snakexLength[i] = this.snakexLength[i - 1];
                }
                if (this.snakexLength[0] < 25) {
                    this.snakexLength[0] += SPEED;
                    dead();
                }
            }
        }
    }

    public void movementUp(){
        if (!generalCheck()){
            dead();
        }
        if (this.moves != 0 && !this.death) {
            this.snakexLength = updateArray(snakexLength);
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakeyLength[i] = this.snakeyLength[i] - SPEED;
                } else {
                    this.snakeyLength[i] = this.snakeyLength[i - 1];
                }
                if (this.snakeyLength[0] < 73) {
                    this.snakeyLength[0] += SPEED;
                    dead();
                }
            }
        }
    }

    public void movementDown(){
        if (!generalCheck()){
            dead();
        }
        if (this.moves != 0 && !this.death) {
            this.snakexLength = updateArray(snakexLength);
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakeyLength[i] = this.snakeyLength[i] + SPEED;
                } else {
                    this.snakeyLength[i] = this.snakeyLength[i - 1];
                }
                if (this.snakeyLength[0] > 568) {
                    this.snakeyLength[0] -= SPEED;
                    dead();
                }
            }
        }
    }

    public void setNextDirection(Direction nxt) {
        this.currentDirection = nxt;
    }

    public void updateDirection() {
        this.currentDirection = this.nextDirection;
    }

    public void move() {
        // move head according to current direction
        switch (currentDirection) {
            case RIGHT:
                movementRight();
                break;
            case LEFT:
                movementLeft();
                break;
            case UP:
                movementUp();
                break;
            case DOWN:
                movementDown();
                break;
            case NONE:
                break;
        }
    }

    private void defaultActions(){
        moves++;
    }

    public void restart() {
        this.nextDirection = startingDirection;
        this.currentDirection = startingDirection;
        defaultActions();
    }

    public void reset(){
        moves= 0;
        lengthOfSnake= snakeInitialSize;
        death = false;
    }
}