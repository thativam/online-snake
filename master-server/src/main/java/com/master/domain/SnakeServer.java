package com.master.domain;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.snake.communication.servClient.Redirect;
import com.snake.server.service.ISnakeChildService;
import com.snake.server.service.SnakeChildService;

public class SnakeServer implements ISnakeServer {
    private Server server;
    private ISnakeChildService childService;
    private MatchMakingPlayers matchMakingPlayers;
    // TODO: how to control this list? i.e when to remove server
    private Set<Integer> connections = Collections.synchronizedSet( new HashSet<>()); // store just the port, but should save host+port for each generated server.
    private static final int MAX_PLAYERS = 4;
    private static final Logger logger = LoggerFactory.getLogger(SnakeServer.class);

    public SnakeServer() {
        this.server = new Server();
        this.childService = new SnakeChildService();
    }

    public void setMatchMakingPlayers(MatchMakingPlayers matchMakingPlayers) {
        this.matchMakingPlayers = matchMakingPlayers;
    }

    public void start() {
        server.start();
    }

    public void bind(int port) throws IOException {
        server.bind(port);
    }

    public void register(Class<?> clazz) {
        server.getKryo().register(clazz);
    }

    public void addClientListener() {
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                logger.info("Client connected to master from " + connection.getRemoteAddressTCP());
                
                try {
                    // logic to create/use a child server
                    // Create and start a child server on a dynamic port
                    int port = handleNewServers() == -1 ? childService.startChild() : handleNewServers();
                    logger.info("Spun up child server on port " + port);
                    connections.add(port);

                    // Prepare the redirect message with the new port number
                    Redirect redirect = new Redirect(port, "localhost");
                    int bytes = connection.sendTCP(redirect);
                    logger.info("Sent redirect message to client. Bytes sent: " + bytes);                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof String && object.equals("ACK")) {
                    logger.info("Client ACK ? " + object);
                    connection.close();
                }  
            }

            @Override
            public void disconnected(Connection connection) {
                logger.info("Client disconnected from master");
            }
        });
    }

    private int handleNewServers(){
        Map<Integer, Connection[]> activeConnections = childService.getActiveConnections();
        if(matchMakingPlayers == null) {
            logger.info("No strategy for matchmaking");
            return childService.startChild();
        }
        Integer port = matchMakingPlayers.handleNewPlayers(activeConnections, MAX_PLAYERS);
        return port == -1 ? childService.startChild() : port;
    }
}
