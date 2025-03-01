package com.snake.client.service;

import java.io.IOException;
import java.util.List;

public interface ISnakeClientService {
    public void startClient(List<Class<?>> classes);

    public void connectToServer();

    public int sendTCPStringMs(String message) throws IOException;

    public int sendTCPStringSv(String message) throws IOException;

}
