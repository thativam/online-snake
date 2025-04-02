package com.snake.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryonet.Client;
import com.snake.client.service.ISnakeClientService;
import com.snake.client.service.SnakeClientService;
import com.snake.client.domain.SnakeClient;
import com.snake.communication.servClient.Redirect;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        
        ISnakeClientService service = new SnakeClientService(new SnakeClient(new Client()));
        service.startClient(new ArrayList<>(List.of(Redirect.class, String.class)));


        try {
            service.connectToServer();
            service.sendTCPStringMs("Hello from client");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Press Enter to exit...");
        try {
            System.in.read();
        } catch (IOException e) {
            logger.error("Error reading input", e);
            e.printStackTrace();
        }
       
    }
}
