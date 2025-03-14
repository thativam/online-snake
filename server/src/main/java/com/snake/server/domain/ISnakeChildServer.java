package com.snake.server.domain;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;

public interface ISnakeChildServer {
    public void start();

    public void bind(int port) throws IOException;

    public void register(Class<?> clazz);

    public void addClientListener();

    public Connection[] getActiveConnections();

    public Integer getPort();
}
