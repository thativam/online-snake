package com.snake.server.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class ChildServerUtils {
    private ChildServerUtils instance;

    private ChildServerUtils() {
    }

    public ChildServerUtils getInstance() {
        if (instance == null) {
            instance = new ChildServerUtils();
        }
        return instance;
    }

    public static int findFreePorts() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
