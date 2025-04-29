package com.snake.server.domain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.communication.gameInfo.SnakeDto;
import com.snake.communication.servClient.Redirect;

public class SnakeChildServer implements ISnakeChildServer {
    private Server server;
    private Integer port;
    private static final Logger logger = LoggerFactory.getLogger(SnakeChildServer.class);

    public SnakeChildServer() {
        this.server = new Server();
    }

    public Integer getPort() {
        return port;
    }

    public void start() {
        logger.info("[SERVER] Starting child server");
        server.getKryo().register(Redirect.class);
        server.getKryo().register(String.class);
        server.getKryo().register(SnakeDto.class);
        server.getKryo().register(int[].class);
        server.start();
    }

    public void bind(int port) throws IOException {
        this.port = port;
        server.bind(port);
    }

    public void register(Class<?> clazz) {
        server.getKryo().register(clazz);
    }

    public void addClientListener() {
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                logger.info("[SERVER] Client connected to child server");
                connection.sendTCP("[SERVER] Hello World from Child Server!");
            }

            @Override
            public void received(Connection connection, Object object) {
                // This will have to deal with the game events
                // and the snake objects
                // TODO: passing the responsability to the correct funtcion ;
                switch (object.getClass().getSimpleName()) {
                    case "SnakeDto":
                        SnakeDto snakeDto = (SnakeDto) object;
                        logger.info("[SERVER] Received SnakeDto: " + snakeDto.getSnakexLength()[0] + ", "
                                + snakeDto.getSnakeyLength()[0] + ", "  + snakeDto.isDeath() + ", "
                                + snakeDto.getNextDirection());
                        break;
                
                    default:
                        break;
                }
                logger.info(
                        "[SERVER] Received object: " + object + " from client: " + connection.getRemoteAddressTCP());
            }

            @Override
            public void disconnected(Connection connection) {
                logger.info("[SERVER] Client disconnected from child server");
            }
        });
    }

    public Connection[] getActiveConnections() {
        logger.info("[SERVER] Active connections: " + server.getConnections().length);

        return server.getConnections();
    }
}
