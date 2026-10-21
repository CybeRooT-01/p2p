#!/bin/bash
echo "Démarrage du Nœud 1 (port 5000)..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=node1
