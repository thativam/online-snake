package com.snake.client.application.src;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class Snake {

    



    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];
    static int snakeInitialSize = 5;
    int lengthOfSnake;
    int moves;
    final static int SPEED = 6;

    boolean left;
    boolean right;
    boolean up;
    boolean down;
    boolean death;

    public Snake(){
        this.left=false;
        this.right=false;
        this.up=false;
        this.down=false;
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
        if (!this.left && this.moves != 0 && !this.death) {
                this.moves++;
                if (!this.left) {
                    this.right = true;
                } else {
                    this.right = false;
                    this.left = true;
                }
                this.up = false;
                this.down = false;
        }
    }

    public void moveLeft(){
        if (!this.right && this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    public void moveUp(){
        if (!this.down && this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void moveDown(){
        if (!this.up && this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void dead() {
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.death = true;
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

}