package com.master;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.master.service.IMasterServer;
import com.master.service.MasterServer;
import com.snake.communication.servClient.Redirect;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        IMasterServer snakServer = new MasterServer();
        List<Class<?>> communicateClass = new ArrayList<Class<?>>();
        communicateClass.add(Redirect.class);
        communicateClass.add(String.class);
        snakServer.start(3003, communicateClass );
        logger.info("Hello World!");
    }
}
