package com.distributedsystem.p2p.services;

import com.distributedsystem.p2p.network.PeerClient;
import com.distributedsystem.p2p.network.PeerConfig;
import com.distributedsystem.p2p.replication.ReplicationManager;
import com.distributedsystem.p2p.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FileService {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ReplicationManager replicationManager;

    @Autowired
    private PeerConfig config;

    @Autowired
    private PeerClient peerClient;

    public void saveFile(String filename, byte[] data) throws IOException {
        saveFile(filename, data, true);
    }

    public void saveFile(String filename, byte[] data, boolean replicate) throws IOException {
        storageService.saveFile(filename, data);
        
        if (replicate) {
            replicationManager.replicateFile(filename, data);
        }
    }

    public byte[] getFile(String filename) throws IOException {
        byte[] data = storageService.readFile(filename);
        if (data != null) {
            log.info("Fichier {} trouvé localement", filename);
            return data;
        }

        log.info("Fichier {} non trouvé localement, recherche chez les peers", filename);
        return searchInPeers(filename);
    }

    private byte[] searchInPeers(String filename) {
        for (String peer : config.getPeers()) {
            try {
                byte[] data = peerClient.requestFile(peer, filename);
                if (data != null) {
                    log.info("Fichier {} trouvé chez {}", filename, peer);
                    return data;
                }
            } catch (Exception e) {
                log.error("Erreur recherche chez {}: {}", peer, e.getMessage());
            }
        }
        return null;
    }

    public List<String> listFiles() throws IOException {
        return storageService.listFiles();
    }
}
