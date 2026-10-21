package com.distributedsystem.p2p.controllers;

import com.distributedsystem.p2p.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/{filename}")
    public ResponseEntity<String> upload(
            @PathVariable String filename,
            @RequestBody byte[] data,
            @RequestParam(required = false, defaultValue = "true") boolean replicate) {
        try {
            fileService.saveFile(filename, data, replicate);
            return ResponseEntity.ok("Fichier " + filename + " uploadé avec succès");
        } catch (Exception e) {
            log.error("Erreur upload: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        try {
            byte[] data = fileService.getFile(filename);
            if (data == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            log.error("Erreur download: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> listFiles() {
        try {
            return ResponseEntity.ok(fileService.listFiles());
        } catch (Exception e) {
            log.error("Erreur listage: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
