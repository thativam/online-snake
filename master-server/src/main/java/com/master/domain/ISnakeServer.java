package com.master.domain;

import java.io.IOException;

public interface ISnakeServer {
    public void start();

    public void bind(int port) throws IOException;

    public void register(Class<?> clazz);

    public void addClientListener();
}
