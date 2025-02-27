package com.snake.client.domain;

import com.esotericsoftware.kryonet.Listener;

public interface ISnakeClient {
    public void start();

    public void register(Class<?> clazz);

    public void addListener(Listener listener);

    public void connect(int timeout, String host, int port);

}
