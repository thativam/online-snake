package com.master.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.communication.servClient.Redirect;
import com.snake.server.service.ISnakeChildService;
import com.snake.server.service.SnakeChildService;

public class SnakeServer implements ISnakeServer {
    private Server server;
    private ISnakeChildService childService;
    private List<Connection> connections = Collections.synchronizedList(new ArrayList<>());

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
                connections.add(connection);
                try {
                    // Create and start a child server on a dynamic port
                    int port = childService.startChild();
                    System.out.println("Spun up child server on port " + port);

                    // Prepare the redirect message with the new port number
                    Redirect redirect = new Redirect(port, "localhost");
                    int bytes = connection.sendTCP(redirect);
                    System.out.println("Sent redirect message to client. Bytes sent: " + bytes);                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof String && object.equals("ACK")) {
                    System.out.println("Client ACK ? " + object);
                    connection.close();
                }  
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected from master");
                connections.remove(connection);
            }
        });
    }
}
