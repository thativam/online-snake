package com.snake.client.domain.aplication;

public enum Direction {
    RIGHT, LEFT, UP, DOWN, NONE;

    public boolean isOpposite(Direction direction) {
        return this == LEFT && direction == RIGHT || this == RIGHT && direction == LEFT
                || this == UP && direction == DOWN || this == DOWN && direction == UP;
    }

    public int getIntValue() {
        switch (this) {
            case RIGHT:
                return 0;
            case LEFT:
                return 1;
            case UP:
                return 2;
            case DOWN:
                return 3;
            default:
                return -1; // or throw an exception
        }
    }
}
