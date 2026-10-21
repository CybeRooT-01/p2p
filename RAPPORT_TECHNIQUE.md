# Rapport technique - Système P2P de partage de fichiers

## 1. Contexte du projet

Ce projet met en place un système distribué de partage de fichiers.  
L'équipe développe une application en architecture pair-à-pair (P2P).  
Le système permet à plusieurs nœuds de collaborer sans serveur central unique.

Le projet répond à quatre besoins principaux :
- un utilisateur dépose un fichier sur un nœud ;
- le système réplique le fichier sur d'autres nœuds ;
- un utilisateur retrouve un fichier même s'il n'est pas local ;
- le système continue de fonctionner quand un nœud devient indisponible.

## 2. Architecture du système

### 2.1 Vue générale

Le système suit une architecture P2P.  
Chaque nœud joue un rôle actif dans le stockage et dans les échanges réseau.  
Chaque nœud expose des API REST pour l'upload, le download et la consultation des fichiers.

Le système contient trois nœuds backend :
- Nœud 1 : port 5000 ;
- Nœud 2 : port 5001 ;
- Nœud 3 : port 5002.

Le système contient aussi une interface web sur le port 4200.  
L'interface permet à l'utilisateur de piloter les opérations sur les nœuds.

### 2.2 Composants backend

Le backend Java Spring Boot utilise les composants suivants :
- `FileController` gère les points d'entrée HTTP ;
- `FileService` applique la logique métier de gestion des fichiers ;
- `FileStorageService` gère le stockage local de chaque nœud ;
- `PeerConfig` déclare les peers et la configuration réseau ;
- `PeerClient` communique avec les autres nœuds ;
- `ReplicationManager` orchestre la réplication des fichiers.

Cette séparation améliore la lisibilité et facilite la maintenance du code.

### 2.3 Flux fonctionnels principaux

**Upload d'un fichier**  
L'utilisateur envoie un fichier vers un nœud via l'API REST.  
Le nœud stocke d'abord le fichier localement.  
Le gestionnaire de réplication copie ensuite le fichier vers un peer.

**Download d'un fichier**  
Un utilisateur demande un fichier depuis un nœud actif.  
Le nœud vérifie d'abord son stockage local.  
Si le fichier n'existe pas localement, le nœud interroge les peers.  
Le nœud retourne le fichier dès qu'il trouve une copie disponible.

**Tolérance aux pannes**  
Le système garde plusieurs copies d'un même fichier.  
Si un nœud tombe, un autre nœud peut encore servir le fichier.  
Cette stratégie limite l'impact d'une panne isolée.

## 3. Rapport technique

### 3.1 Technologies utilisées

Le backend utilise Java 17 et Spring Boot 4.0.5.  
Le projet utilise Maven pour la gestion des dépendances et la construction.  
Le code utilise Lombok pour réduire le code répétitif.  
Le frontend utilise Angular avec Tailwind CSS pour l'interface.

### 3.2 Configuration des nœuds

Chaque nœud possède un fichier de configuration dédié (`application-nodeX.yml`).  
Chaque fichier définit :
- un port d'écoute différent ;
- un répertoire de stockage local ;
- une liste de peers pour les échanges distribués.

Cette configuration permet de simuler un petit cluster P2P sur une seule machine.

### 3.3 Démarrage et exploitation

Le projet fournit des scripts simples :
- `start-node1.sh`, `start-node2.sh`, `start-node3.sh` pour lancer les nœuds ;
- `start-frontend.sh` pour lancer l'interface Angular ;
- `demo.sh` pour exécuter une démonstration du backend.

La procédure d'usage suit ces étapes :
1. nettoyer les anciens stockages ;
2. démarrer les trois nœuds ;
3. démarrer le frontend ;
4. exécuter des opérations d'upload et de download.

### 3.4 Scénario de test représentatif

Le scénario de validation suit ce déroulement :
1. un utilisateur charge un fichier sur le nœud 1 ;
2. le système réplique le fichier sur un autre nœud ;
3. un utilisateur télécharge le fichier depuis le nœud 2 ;
4. l'équipe arrête le nœud 1 et vérifie que le fichier reste disponible.

Ce scénario valide la réplication, la recherche distribuée et la résilience.

### 3.5 Points forts observés

Le système montre une architecture claire et modulaire.  
Le système propose une expérience simple grâce au frontend.  
Le système démontre les principes clés des systèmes distribués.

### 3.6 Limites techniques actuelles

Le projet documente une réplication automatique, mais il ne précise pas encore la stratégie avancée de cohérence.  
Le projet ne décrit pas un mécanisme complet de sécurité (authentification, autorisation, chiffrement).  
Le projet ne présente pas encore des métriques de performance détaillées.

### 3.7 Recommandations d'amélioration

L'équipe peut ajouter une authentification des requêtes entre nœuds.  
L'équipe peut ajouter une vérification d'intégrité avec hash de fichier.  
L'équipe peut ajouter une gestion de version pour les conflits de réplication.  
L'équipe peut ajouter des tests de charge et des tableaux de mesures.

## 4. Conclusion

Le projet réalise un système P2P fonctionnel et pédagogique.  
Le système répond aux besoins de partage, de réplication et de tolérance aux pannes.  
L'architecture actuelle constitue une base solide pour des évolutions futures.