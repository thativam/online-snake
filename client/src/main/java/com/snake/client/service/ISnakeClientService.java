package com.snake.client.service;

import java.util.List;

public interface ISnakeClientService {
    public void startClient(List<Class<?>> classes);

    public void connectToServer();

}
