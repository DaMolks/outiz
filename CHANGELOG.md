# Changelog

## [Unreleased]

### Added
- Ressources de couleurs Material Design Light
- Configuration de thème sans barre d'action par défaut
- Toolbar personnalisé dans le layout principal
- Ajout des fichiers d'icône launcher foreground pour les densités HDPI et MDPI
- Ajout de la ressource string 'add_time_entry' pour le bouton d'ajout d'entrée de temps

### Fixed
- Résolution des erreurs de compilation liées aux ressources de couleurs
- Correction de la configuration de la barre d'action dans MainActivity
- Correction des ressources pour l'icône launcher

### Changed
- Mise à jour de la configuration du thème pour utiliser un Toolbar personnalisé
- Ajustement de la structure du layout principal

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

### 2024-01-08 10:00 UTC
- Ajout de la ressource string 'add_time_entry' pour les rapports

## Contributing
Lors de l'ajout de modifications au changelog, veuillez suivre ces directives :
- Utilisez [Semantic Versioning](https://semver.org/)
- Regroupez les modifications par catégories : Added, Changed, Deprecated, Removed, Fixed, Security
- Incluez la date et l'heure précises, ainsi qu'une description claire