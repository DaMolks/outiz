# Instructions pour Claude

## Contexte du projet
Outiz est une application Android développée en Kotlin pour gérer des rapports d'intervention. L'application utilise une architecture MVVM avec Room pour la persistance des données.

## État actuel et dernières actions
- Dernière action : Configuration des fichiers Gradle pour résoudre les problèmes de compilation
- Les modèles de données et DAOs sont implémentés
- Les layouts de base sont créés
- Les chaînes de caractères sont configurées dans strings.xml

## Arborescence et fichiers clés
- `app/src/main/java/com/example/outiz/`
  - `models/` : Technician.kt, Site.kt, Report.kt
  - `data/` : OutizDatabase.kt, Converters.kt
  - `data/dao/` : TechnicianDao.kt, SiteDao.kt, ReportDao.kt
  - `ui/` : Fragments et ViewModels

## Prochaines étapes prioritaires
1. Implémenter et tester le flux de navigation de base
2. Implémenter la création/édition des sites
3. Implémenter la création/édition des rapports
4. Ajouter la signature des rapports
5. Implémenter l'export PDF
6. Mettre en place la synchronisation Google Drive

## Informations techniques importantes
- Les commits précédents ont tous été faits avec le format :
  ```json
  antml:function_calls
  antml:invoke name="push_files"
  antml:parameter name="repo">outiz</parameter
  antml:parameter name="files">[{"path": "...", "content": "..."}]
  ```
- Le token GitHub est configuré en "Classic" pour éviter les problèmes de permission

## Commandes utiles utilisées
- Les push de fichiers se font toujours avec l'API GitHub via les commandes ci-dessus
- En cas d'erreur -32603, vérifier le format du contenu et des paramètres

## Notes pour référence
1. Architecture choisie :
   - MVVM pour séparer la logique métier
   - Room pour la persistance
   - ViewBinding pour les vues
   - Navigation Component pour la navigation
   - Coroutines pour l'asynchrone

2. Modules prévus :
   - Rapports (en cours)
   - Sites (en cours)
   - Possibilité d'ajouter d'autres modules à l'avenir

## Éléments spécifiques à garder en tête
- L'application doit rester modulaire pour faciliter l'ajout de futures fonctionnalités
- Les rapports doivent pouvoir être personnalisables (sections activables/désactivables)
- Le profil technicien est obligatoire au premier lancement

## À faire immédiatement
1. Vérifier que l'application compile et se lance correctement
2. Aider l'utilisateur à tester les fonctionnalités de base
3. Continuer l'implémentation dans l'ordre des priorités

## Dernier statut de l'application
L'application ne compile pas encore parfaitement - il reste quelques ajustements à faire au niveau des dépendances Gradle et de la configuration. Une fois ces problèmes résolus, on pourra passer aux tests des fonctionnalités de base.