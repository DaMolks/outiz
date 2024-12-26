# État actuel du projet Outiz

## Structure actuelle

### Modèles de données
- `Technician` : Profil du technicien (nom, prénom, secteur, identifiant, chef)
- `Site` : Informations du site (nom, adresse, code S, client)
- `Report` : Rapport d'intervention (avec sections modulaires)
- `TimeEntry` : Entrées de temps pour les rapports

### Base de données
- Configuration Room mise en place
- DAOs créés pour tous les modèles
- Convertisseurs pour les types complexes (dates, listes)

### Interface utilisateur
1. Écran de lancement (LaunchScreen)
   - Vérifie si un profil technicien existe
   - Redirige vers la création de profil ou l'écran principal

2. Création du profil technicien
   - Formulaire de saisie des informations
   - Stockage dans Room et SharedPreferences

3. Écran principal avec modules
   - Liste des modules disponibles (pour l'instant seul "Rapports" est implémenté)
   - Structure modulaire prête pour l'ajout de futures fonctionnalités

4. Gestion des sites
   - Liste des sites
   - Création/édition de site
   - Suppression de site

5. Gestion des rapports
   - Liste des rapports avec filtres (tous, semaine, mois)
   - Création/édition de rapport avec onglets :
     - Informations générales
     - Suivi du temps
     - Photos

## À implémenter
1. Signature des rapports
2. Export PDF
3. Synchronisation Google Drive

## Points techniques importants
- Architecture MVVM utilisée
- Navigation avec Navigation Component
- Base de données avec Room
- ViewBinding pour les vues
- Coroutines pour l'asynchrone

## Dernier état
Le projet compile mais nécessite des tests pour valider le bon fonctionnement des fonctionnalités implémentées.

## Fichiers de configuration importants
- Gradle correctement configuré avec AndroidX
- Toutes les chaînes de caractères dans strings.xml
- Permissions nécessaires dans le Manifest

## Notes pour la suite
- Tester chaque fonctionnalité déjà implémentée
- Implémenter la signature des rapports en priorité
- Ajouter la génération PDF
- Mettre en place la synchronisation avec Google Drive