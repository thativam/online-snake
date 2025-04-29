package com.snake.client.domain;

public interface ServerSubscriber {
    void updateData(GameEvents event, Object data);
}
