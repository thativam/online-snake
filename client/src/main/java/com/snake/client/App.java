package com.snake.client;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.snake.communication.servClient.Redirect;


public class App 
{
    public static void main(String[] args) {
        Client snakeClient = new Client();
        snakeClient.start();
        snakeClient.getKryo().register(Redirect.class);
        snakeClient.getKryo().register(String.class); 
        System.out.println("Client starting ...   ");
        snakeClient.addListener(new Listener() {
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
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //snakeClient.close();
                    connectToChildServer(redirect.getPort());
                }
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected from server");
            }
        });

        try {
            // Connect to the snake server at localhost:3003 (adjust host if needed)
            snakeClient.connect(5000, "localhost", 3003);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connectToChildServer(int childPort) {
        Client childClient = new Client();
        childClient.getKryo().register(Redirect.class);
        childClient.getKryo().register(String.class);
        childClient.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof String) {
                    String message = (String) object;
                    System.out.println("Message from child server: " + message);
                    //childClient.close();
                }
            }
        });

        childClient.start();
        try {
            childClient.connect(5000, "localhost", childPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
