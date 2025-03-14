package com.master.service;

import java.io.IOException;
import java.util.List;

import com.master.domain.DefaultMatchMaking;
import com.master.domain.ISnakeServer;
import com.master.domain.MatchMakingPlayers;
import com.master.domain.SnakeServer;

public class MasterServer implements IMasterServer {
    private ISnakeServer server;

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
            System.out.println("Error while trying to bind port" + e);
        }
    }

    public void stop() {
        System.out.println("Master server stopped");
    }

    public void restart() {
        System.out.println("Master server restarted");
    }

    public void status() {
        System.out.println("Master server status");
    }

    public void exit() {
        System.out.println("Master server exit");
    }
}