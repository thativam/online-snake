package com.snake.server.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SnakeChildServer implements ISnakeChildServer {
    private Server server;

    public SnakeChildServer() {
        this.server = new Server();
    }

    public void start() {
        server.start();
    }

    public void bind(int port) throws IOException {
        server.bind(port);
    }

    public void register(Class<?> clazz) {
        server.getKryo().register(clazz);
    }

    public void addClientListener() {
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected to child server");
                connection.sendTCP("Hello World from Child Server!");
            }
        });
    }
}
