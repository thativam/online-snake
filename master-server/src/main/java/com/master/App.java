package com.master;

import java.util.ArrayList;
import java.util.List;

import com.master.service.IMasterServer;
import com.master.service.MasterServer;
import com.snake.communication.servClient.Redirect;

public class App {
    public static void main(String[] args) {
        IMasterServer snakServer = new MasterServer();
        List<Class<?>> communicateClass = new ArrayList<Class<?>>();
        communicateClass.add(Redirect.class);
        communicateClass.add(String.class);
        snakServer.start(3003, communicateClass );
        System.out.println("Hello World!");
    }
}
