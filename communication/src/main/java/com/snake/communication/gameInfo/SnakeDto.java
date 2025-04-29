package com.snake.communication.gameInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SnakeDto {
    
    int nextDirection;
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];
    boolean death;
}
