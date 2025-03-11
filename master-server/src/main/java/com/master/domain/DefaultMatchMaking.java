package com.master.domain;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;

public class DefaultMatchMaking implements MatchMakingPlayers {
    @Override
    public int handleNewPlayers(Map<Integer, Connection[]> activeConnections, Integer max_players) {
        if(activeConnections.isEmpty()) {
            System.out.println("No active connections");
            return -1;
        }

        @SuppressWarnings("unused")
        SortedMap<Integer, Connection[]> sortedConnections = activeConnections.entrySet().stream()
            .sorted((entry1, entry2) -> Integer.compare(entry1.getValue().length, entry2.getValue().length))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, TreeMap::new));
        
        while ((sortedConnections.size() > 0) && (sortedConnections.get(sortedConnections.lastKey()).length == max_players)) {
            sortedConnections.remove(sortedConnections.lastKey());
        }

        return sortedConnections.isEmpty() ? -1 : sortedConnections.lastKey(); // with the most connection
    }
}