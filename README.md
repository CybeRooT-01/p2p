# Système P2P de Partage de Fichiers

Projet de Systèmes d'Information Distribués - UNCHK P8 Master 1 IL

## Architecture

- Architecture P2P
- Réplication automatique des fichiers
- Recherche distribuée
- Tolérance aux pannes

## Fonctionnalités

Upload de fichiers  
Download de fichiers  
Réplication automatique sur les peers  
Recherche distribuée (si fichier absent localement)  
Tolérance aux pannes (fichiers accessibles même si un nœud tombe)  
Liste des fichiers locaux  

## Démarrage Rapide

### 0. Nettoyer les données précédentes

```bash
rm -rf storage_node_*
```

### 1. Lancer les 3 nœuds et le front (dans 4 terminaux différents)

```bash
# Terminal 1 - Nœud 1 (port 5000)
./start-node1.sh

# Terminal 2 - Nœud 2 (port 5001)
./start-node2.sh

# Terminal 3 - Nœud 3 (port 5002)
./start-node3.sh

# Terminal 4 - Frontend (port 4200)
./start-frontend.sh
```

### 2. Lancer la démonstration complète du backend

```bash
./demo.sh
```

## Frontend


Accédez à l'interface sur: http://localhost:4200

### Fonctionnalités de l'interface

- Sélection du nœud actif
- Visualisation de l'état des nœuds
- Upload de fichiers avec interface intuitive
- Liste des fichiers disponibles
- Téléchargement de fichiers
- Design moderne avec Tailwind CSS


## Scénario de Démonstration

1. **Upload**: Ajouter un fichier sur le Nœud 1
2. **Réplication**: Le fichier est automatiquement copié sur un peer
3. **Recherche**: Télécharger le fichier depuis le Nœud 2 (recherche distribuée)
4. **Tolérance aux pannes**: Arrêter le Nœud 1, le fichier reste accessible depuis le Nœud 2

## Structure du Projet

```
src/main/java/com/distributedsystem/p2p/
├── controllers/
│   └── FileController.java          # API REST
├── services/
│   └── FileService.java              # Logique métier
├── storage/
│   └── FileStorageService.java       # Stockage local
├── network/
│   ├── PeerConfig.java               # Configuration des peers
│   └── PeerClient.java               # Communication P2P
├── replication/
│   └── ReplicationManager.java       # Gestion réplication
└── P2pApplication.java               # Point d'entrée
```

## Configuration

Chaque nœud a sa propre configuration (application-nodeX.yml):

- Port différent (5000, 5001, 5002)
- Répertoire de stockage différent
- Liste des peers

## Technologies

- Java 17 (je sais c'est pas 
- Spring Boot 4.0.5
- Maven
- Lombok

## Scripts Disponibles

- `start-node1.sh`: Démarrer le nœud 1
- `start-node2.sh`: Démarrer le nœud 2
- `start-node3.sh`: Démarrer le nœud 3
- `start-frontend.sh`: Démarrer l'interface web Angular
- `demo.sh`: Démonstration automatique complète du back

## Auteurs
1-Thierno Birahim Gueye
  N00606020201
  thiernobirahim.gueye@unchk.edu.sn

2- El Hadj Malick Ndao
  elhadjimalick.ndao@unchk.edu.sn
  N006603120201
