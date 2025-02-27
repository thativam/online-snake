package com.snake.client.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Listener;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SnakeClient implements ISnakeClient {
    private Client client;

    @Override
    public void start() {
        client.start();
    }

    @Override
    public void register(Class<?> clazz) {
        client.getKryo().register(clazz);
    }

    @Override
    public void addListener(Listener listener) {
        client.addListener(listener);
    }

    @Override
    public void connect(int timeout, String host, int port) {
        try {
            client.connect(timeout, host, port);
            // ADD delay if needed.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
