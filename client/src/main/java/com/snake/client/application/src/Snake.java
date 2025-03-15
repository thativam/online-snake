package com.snake.client.application.src;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Snake {

    enum Direction {
        LEFT, RIGHT, UP, DOWN, NONE;

        public boolean isOpposite(Direction direction) {
            return this == LEFT && direction == RIGHT || this == RIGHT && direction == LEFT
                    || this == UP && direction == DOWN || this == DOWN && direction == UP;
        }
    }


    Direction currentDirection;

    Direction nextDirection;

    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];
    static int snakeInitialSize = 5;
    int lengthOfSnake;
    int moves;
    final static int SPEED = 6;

    boolean death;




    public Snake(Direction currentDirection) {
        this.currentDirection = currentDirection; // or any initial direction
        this.nextDirection = currentDirection;
        this.death=false;
        this.lengthOfSnake=5;
        this.moves=0;
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

    public int start(int snakeHeadXPos){
        for (int i = 0; i < snakeInitialSize; i++) {
            this.snakexLength[i] = snakeHeadXPos;
            snakeHeadXPos -= SPEED;
            this.snakeyLength[i] = 355;
        }
        return snakeHeadXPos;
    }

    public void moveRight(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!currentDirection.isOpposite(Direction.RIGHT)) {
                currentDirection = Direction.RIGHT;
            } else {
                currentDirection  = Direction.LEFT;
            }
        }
    }

    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!currentDirection.isOpposite(Direction.LEFT)) {
                currentDirection = Direction.LEFT;
            } else {
                currentDirection = Direction.RIGHT;
            }
        }
    }

    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!currentDirection.isOpposite(Direction.UP)) {
                currentDirection = Direction.UP;
            } else {
                currentDirection = Direction.DOWN;

            }
        }
    }

    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!currentDirection.isOpposite(Direction.DOWN)) {
                currentDirection = Direction.DOWN;
            } else {
                currentDirection = Direction.UP;
            }
        }
    }

    public void dead() {
        this.death = true;
        this.currentDirection = Direction.NONE;
    }

    public void movementRight(){
        if (!generalCheck()){
            dead();
        }
        if (this.moves != 0 && !this.death) {
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakeyLength[i + 1] = this.snakeyLength[i];
            }
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
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakeyLength[i + 1] = this.snakeyLength[i];
            }
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
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakexLength[i + 1] = this.snakexLength[i];
            }
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
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakexLength[i + 1] = this.snakexLength[i];
            }
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

    public void restart() {
        this.currentDirection = Direction.RIGHT;
        defaultActions();  
    }

    private void defaultActions(){
        moves++;
    }
    public void restart(Direction direction) {
        this.currentDirection = direction;
        defaultActions();
    }

    public void reset(){
        moves=0;
        lengthOfSnake= snakeInitialSize;
        death = false;
    }
}