package com.snake.server.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryonet.Connection;
import com.snake.server.domain.ISnakeChildServer;
import com.snake.server.domain.SnakeChildServer;
import com.snake.server.utils.ChildServerUtils;

public class SnakeChildService implements ISnakeChildService {
    private ISnakeChildServer server;
    private List<ISnakeChildServer> activeServers = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(SnakeChildService.class);

    public List<ISnakeChildServer> getActiveServers() {
        return activeServers;
    }

    public int startChild() {
        server = new SnakeChildServer();
        int port = ChildServerUtils.findFreePorts();
        if (port == -1) {
            logger.info("No free ports available");
            return -1;
        }
        try {
            server.bind(port);
            server.addClientListener();
            server.start();
            activeServers.add(server); // TODO how to control this list? i.e when to remove server
            logger.info("[SERVER] Child server started on port " + port);
            logger.info("[SERVER] Wainting for client to connect...");
        } catch (IOException e) {
            logger.error("Error binding child server to port " + port + " " + e.getMessage());
        }
        return port;
    }

    public void stopChild() {
    }

    public Map<Integer, Connection[]> getActiveConnections() {
        HashMap<Integer, Connection[]> activeConnections = new HashMap<>();
        for (ISnakeChildServer server : activeServers) {
            activeConnections.put(server.getPort(), server.getActiveConnections());
            server.getActiveConnections();
        }
        return activeConnections;
    }

}
