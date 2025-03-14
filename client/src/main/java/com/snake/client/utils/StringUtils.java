package com.snake.client.utils;

import java.util.List;

public class StringUtils {
    private StringUtils() {
    }

    public static String stringFormat(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(i -> sb.append(i).append("\n"));
        return sb.toString();
    }
}
