package com.distributedsystem.p2p.replication;

import com.distributedsystem.p2p.network.PeerClient;
import com.distributedsystem.p2p.network.PeerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ReplicationManager {

    @Autowired
    private PeerConfig config;

    @Autowired
    private PeerClient peerClient;

    public void replicateFile(String filename, byte[] data) {
        List<String> peers = config.getPeers();
        if (peers.isEmpty()) {
            log.warn("Aucun peer configuré pour la réplication");
            return;
        }
        for (String peer : peers) {
            try {
                peerClient.sendFile(peer, filename, data);
                log.info("Réplication réussie vers {}", peer);
                break; // tbh juste 1 replication suffit... fin jpense
            } catch (Exception e) {
                log.error("Échec réplication vers {}", peer);
            }
        }
    }
}
