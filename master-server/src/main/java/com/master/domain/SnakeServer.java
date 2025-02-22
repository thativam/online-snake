package com.master.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.server.domain.ISnakeChildServer;
import com.snake.server.service.ISnakeChildService;
import com.snake.server.service.SnakeChildService;

public class SnakeServer implements ISnakeServer {
    private Server server;
    private ISnakeChildService childServerice;

    public SnakeServer() {
        this.server = new Server();
        this.childServerice = new SnakeChildService();
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
                System.out.println("Client connected to master from " + connection.getRemoteAddressTCP());
                try {
                    // Create and start a child server on a dynamic port
                    int port = childServerice.startChild();
                    System.out.println("Spun up child server on port " + port);

                    // Prepare the redirect message with the new port number
                    // Redirect redirect = new Redirect();
                    // redirect.port = childPort;
                    // connection.sendTCP(redirect);
                    // Disconnect the client from the master server

                    // Optionally disconnect the client from the master server
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
