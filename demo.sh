#!/bin/bash

echo "=== DÉMONSTRATION SYSTÈME P2P ==="
echo ""

rm -rf storage_node_*
echo "Attente du démarrage des nœuds..."
sleep 2

echo "1. Upload d'un fichier sur le Nœud 1 (port 5000)"
echo "Contenu du fichier" > test.txt
curl -s -X POST http://localhost:5000/files/test.txt --data-binary @test.txt
echo ""
echo "Fichier uploadé"
echo ""

sleep 1

echo "2. Liste des fichiers sur Nœud 1"
curl -s http://localhost:5000/files
echo ""
echo ""

sleep 1

echo "3. Liste des fichiers sur Nœud 2 (réplication automatique)"
curl -s http://localhost:5001/files
echo ""
echo ""

sleep 1

echo "4. Téléchargement depuis Nœud 2"
curl -s http://localhost:5001/files/test.txt
echo ""
echo ""

sleep 1

echo "5. Upload d'un autre fichier sur Nœud 3"
echo "Fichier du nœud 3" > fichier3.txt
curl -s -X POST http://localhost:5002/files/fichier3.txt --data-binary @fichier3.txt
echo ""
echo "Fichier uploadé sur nœud 3"
echo ""

sleep 1

echo "6. Recherche distribuée: télécharger fichier3.txt depuis Nœud 1"
curl -s http://localhost:5000/files/fichier3.txt
echo ""
echo ""

#y'aura surement des caractéeres ascii bidon mais boff le truc c'est pour tester le back directement
