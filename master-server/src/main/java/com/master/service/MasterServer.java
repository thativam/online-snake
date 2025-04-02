package com.master.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.master.domain.DefaultMatchMaking;
import com.master.domain.ISnakeServer;
import com.master.domain.MatchMakingPlayers;
import com.master.domain.SnakeServer;

public class MasterServer implements IMasterServer {
    private ISnakeServer server;
    private static final Logger logger = LoggerFactory.getLogger(MasterServer.class);

    public void start(int port, List<Class<?>> communicateClass) {
        server = new SnakeServer();
        MatchMakingPlayers matchMakingPlayers = new DefaultMatchMaking();
        server.setMatchMakingPlayers(matchMakingPlayers);
        try {
            server.bind(port);
            communicateClass.forEach(c -> {
                server.register(c);
            });
            server.addClientListener();
            server.start();
        } catch (IOException e) {
            logger.error("Error while trying to bind port", e);
        }
    }

    public void stop() {
        logger.info("Master server stopped");
    }

    public void restart() {
        logger.info("Master server restarted");
    }

    public void status() {
        logger.info("Master server status");
    }

    public void exit() {
        logger.info("Master server exit");
    }
}