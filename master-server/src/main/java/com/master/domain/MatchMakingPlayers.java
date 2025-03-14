package com.master.domain;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;

public interface MatchMakingPlayers {
    public int handleNewPlayers(Map<Integer, Connection[]> activeConnections, Integer max_players);
}
