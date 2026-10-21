#!/bin/bash
echo "Démarrage du Nœud 2 (port 5001)..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=node2
