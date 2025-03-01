package com.snake.client.service;

import java.io.IOException;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
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

    public SnakeClientService(ISnakeClient client) {
        this.client = client;
    }
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
                System.out.println("Client disconnected from Master Server");
            }
        });
    }

    @Override
    public void connectToServer() {
        client.connect(5000, "localhost", 3003);
    }

    public ISnakeClient connectToChildServer(int childPort) {
        childClient = new SnakeClient(new Client());
        childClient.start();
        for (Class<?> clazz : classes) {
            client.register(clazz);
        }
        childClient.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                System.out.println("[CLIENT] Message from child server: " + object);
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected from Child Server");
            }
        });
        
        childClient.connect(5000, "localhost", childPort);
        
        childClient.sendString("Hello from client");
        return childClient;
    }


    @Override
    public int sendTCPStringMs(String message) throws IOException {
        int bytesSent = client.sendString(message);
        return bytesSent;
    }

    @Override
    public int sendTCPStringSv(String message) throws IOException {
        if(childClient != null) {
            int bytesSent = childClient.sendString(message);
            return bytesSent;
        }
        return -1;
    }

}
