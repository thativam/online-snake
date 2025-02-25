package com.snake.server.service;

import java.io.IOException;

import com.snake.server.domain.ISnakeChildServer;
import com.snake.server.domain.SnakeChildServer;
import com.snake.server.utils.ChildServerUtils;

public class SnakeChildService implements ISnakeChildService {
    private ISnakeChildServer server;

    public int startChild() {
        server = new SnakeChildServer();
        int port = ChildServerUtils.findFreePorts();
        if (port == -1) {
            System.out.println("No free ports available");
            return -1;
        }
        try {
            server.bind(port);
            server.addClientListener();
            server.start();
            System.out.println("[SERVER] Child server started on port " + port);
            System.out.println("[SERVER] Wainting for client to connect...");
        } catch (IOException e) {
            System.out.println("Error binding child server to port " + port + " " + e.getMessage());
        }
        return port;
    }

    public void stopChild() {
    }

}
