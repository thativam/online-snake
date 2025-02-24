package com.master.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.communication.servClient.Redirect;
import com.snake.server.service.ISnakeChildService;
import com.snake.server.service.SnakeChildService;

public class SnakeServer implements ISnakeServer {
    private Server server;
    private ISnakeChildService childService;

    public SnakeServer() {
        this.server = new Server();
        this.childService = new SnakeChildService();
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
                    int port = childService.startChild();
                    System.out.println("Spun up child server on port " + port);

                    // Prepare the redirect message with the new port number
                    Redirect redirect = new Redirect(port, "localhost");
                    int bytes = connection.sendTCP(redirect);
                    System.out.println("Sent redirect message to client. Bytes sent: " + bytes);
                    //connection.close();
                    //server.dispose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
