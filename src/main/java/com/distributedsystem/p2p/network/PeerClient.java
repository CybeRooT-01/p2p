package com.distributedsystem.p2p.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class PeerClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendFile(String peerUrl, String filename, byte[] data) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            HttpEntity<byte[]> request = new HttpEntity<>(data, headers);
            String url = peerUrl + "/files/" + filename + "?replicate=false";
            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            log.info("Fichier {} envoyé à {}", filename, peerUrl);
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi à {}: {}", peerUrl, e.getMessage());
        }
    }

    public byte[] requestFile(String peerUrl, String filename) {
        try {
            String url = peerUrl + "/files/" + filename;
            return restTemplate.getForObject(url, byte[].class);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération depuis {}: {}", peerUrl, e.getMessage());
            return null;
        }
    }
}
