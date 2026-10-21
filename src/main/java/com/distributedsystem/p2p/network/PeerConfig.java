package com.distributedsystem.p2p.network;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "node")
@Data
public class PeerConfig {
    private String storage;
    private List<String> peers = new ArrayList<>();
}
