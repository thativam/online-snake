package com.snake.server.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.communication.servClient.Redirect;

public class SnakeChildServer implements ISnakeChildServer {
    private Server server;
    private Integer port;

    public SnakeChildServer() {
        this.server = new Server();
    }

    public Integer getPort() {
        return port;
    }

    public void start() {
        System.out.println("[SERVER] Starting child server");
        server.getKryo().register(Redirect.class);
        server.getKryo().register(String.class);
        server.start();
    }

    public void bind(int port) throws IOException {
        this.port = port;
        server.bind(port);
    }

    public void register(Class<?> clazz) {
        server.getKryo().register(clazz);
    }

    public void addClientListener() {
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("[SERVER] Client connected to child server");
                connection.sendTCP("[SERVER] Hello World from Child Server!");
            }

            @Override
            public void received(Connection connection, Object object) {
                System.out.println("[SERVER] Received object: " + object);
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("[SERVER] Client disconnected from child server");
            }
        });
    }

    public Connection[] getActiveConnections() {
        System.out.println("[SERVER] Active connections: " + server.getConnections().length);

        return server.getConnections();
    }
}
