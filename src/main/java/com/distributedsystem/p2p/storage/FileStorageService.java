package com.distributedsystem.p2p.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.distributedsystem.p2p.network.PeerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileStorageService {

    @Autowired
    private PeerConfig config;

    public void saveFile(String filename, byte[] data) throws IOException {
        Path storagePath = Paths.get(config.getStorage());
        Files.createDirectories(storagePath);
        
        Path filePath = storagePath.resolve(filename);
        Files.write(filePath, data);
        log.info("Fichier sauvegardé: {}", filename);
    }

    public byte[] readFile(String filename) throws IOException {
        Path filePath = Paths.get(config.getStorage()).resolve(filename);
        if (!Files.exists(filePath)) {
            return null;
        }
        return Files.readAllBytes(filePath);
    }

    public boolean fileExists(String filename) {
        Path filePath = Paths.get(config.getStorage()).resolve(filename);
        return Files.exists(filePath);
    }

    public List<String> listFiles() throws IOException {
        Path storagePath = Paths.get(config.getStorage());
        if (!Files.exists(storagePath)) {
            return List.of();
        }
        return Files.list(storagePath)
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
    }
}
