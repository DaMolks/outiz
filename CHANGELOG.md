# Changelog

## [Unreleased]

### Added
- Ressources de couleurs Material Design Light
- Configuration de thème sans barre d'action par défaut
- Toolbar personnalisé dans le layout principal
- Ajout des fichiers d'icône launcher foreground pour les densités HDPI et MDPI
- **Nouvelle fonctionnalité de suivi du temps pour les rapports**
  - Ajout d'une classe TimeEntry pour enregistrer les entrées de temps
  - Implémentation de la logique de création et de gestion des entrées de temps
  - Mise à jour du modèle de rapport pour inclure les entrées de temps

### Fixed
- Résolution des erreurs de compilation liées aux ressources de couleurs
- Correction de la configuration de la barre d'action dans MainActivity
- Correction des ressources pour l'icône launcher
- **Correction des problèmes de compilation liés à l'ajout de la fonctionnalité de temps**

### Changed
- Mise à jour de la configuration du thème pour utiliser un Toolbar personnalisé
- Ajustement de la structure du layout principal
- **Refactoring du modèle de rapport pour intégrer le suivi du temps**

## Modification Log

### 2024-01-07 18:30 UTC
- Ajout du fichier `colors.xml` avec les ressources de couleurs Material Design
  - `md_theme_light_primary`
  - `md_theme_light_primaryContainer`
  - `md_theme_light_onPrimary`

### 2024-01-07 18:35 UTC
- Modification de `themes.xml` pour utiliser un thème sans action bar
- Ajout d'un Toolbar personnalisé dans `activity_main.xml`
- Mise à jour de `MainActivity.kt` pour configurer le Toolbar

### 2024-01-07 18:45 UTC
- Correction des fichiers de configuration de l'icône launcher
- Ajout d'une ressource de couleur spécifique pour l'arrière-plan de l'icône

### 2024-01-07 19:00 UTC
- Ajout des fichiers d'icône launcher foreground
- Génération de fichiers PNG pour les densités HDPI et MDPI

### 2024-12-27 
- **Ajout de la fonctionnalité de suivi du temps pour les rapports**
- **Intégration de la classe TimeEntry dans le modèle de données**
- **Mise à jour des DAOs et des relations de base de données**

## Contributing
Lors de l'ajout de modifications au changelog, veuillez suivre ces directives :
- Utilisez [Semantic Versioning](https://semver.org/)
- Regroupez les modifications par catégories : Added, Changed, Deprecated, Removed, Fixed, Security
- Incluez la date et l'heure précises, ainsi qu'une description claire