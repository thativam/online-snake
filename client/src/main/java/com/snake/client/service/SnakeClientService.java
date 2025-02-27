package com.snake.client.service;

import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.snake.client.domain.ISnakeClient;
import com.snake.client.domain.SnakeClient;
import com.snake.communication.servClient.Redirect;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SnakeClientService implements ISnakeClientService {
    private ISnakeClient client;
    private ISnakeClient childClient;
    private List<Class<?>> classes;

    @Override
    public void startClient(List<Class<?>> classes) {
        client.start();
        this.classes = classes;
        for (Class<?> clazz : classes) {
            client.register(clazz);
        }
        System.out.println("Client starting ...   ");
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected to server: " + connection.getRemoteAddressTCP());
            }

            @Override
            public void received(Connection connection, Object object) {
                System.out.println("Received object: " + object);
                if (object instanceof Redirect) {
                    Redirect redirect = (Redirect) object;
                    System.out.println("Received redirect. New server port: " + redirect.getPort());

                    connection.sendTCP("ACK");
                    connectToChildServer(redirect.getPort());
                }
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected from server");
            }
        });
    }

    @Override
    public void connectToServer() {
        client.connect(5000, "localhost", 54555);
    }

    public ISnakeClient connectToChildServer(int childPort) {
        childClient = new SnakeClient();
        childClient.start();
        for (Class<?> clazz : classes) {
            client.register(clazz);
        }
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String message = (String) object;
                    System.out.println("Message from child server: " + message);
                }
            }
        });
        childClient.connect(5000, "localhost", childPort);
        //Add a sleep to receive the message from the child server
        return childClient;
    }

}
