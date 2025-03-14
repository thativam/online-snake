package com.snake.server.service;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

public interface ISnakeChildService {
    public int startChild();

    public void stopChild();

    public Map<Integer, Connection[]> getActiveConnections();
}
