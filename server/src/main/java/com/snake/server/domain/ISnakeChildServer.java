package com.snake.server.domain;

import java.io.IOException;

public interface ISnakeChildServer {
    public void start();

    public void bind(int port) throws IOException;

    public void register(Class<?> clazz);

    public void addClientListener();
}
