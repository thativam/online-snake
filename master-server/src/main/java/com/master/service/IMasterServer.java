package com.master.service;

import java.util.List;

public interface IMasterServer {
    public void start(int port, List<Class<?>> communicateClass);

    public void stop();

    public void restart();

    public void status();

    public void exit();
}
