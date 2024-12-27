# Changelog

## [Unreleased]

### Fixed
- Amélioration majeure de la migration de base de données pour gérer toutes les colonnes manquantes
- Ajout de valeurs par défaut pour toutes les colonnes
- Construction dynamique des requêtes de migration
- Correction de la migration de la base de données pour gérer le taskType manquant
- Ajout d'une valeur par défaut 'STANDARD' pour la colonne taskType
- Amélioration de la robustesse de la migration 3_2

### Added
- Ressources de couleurs Material Design Light
- Configuration de thème sans barre d'action par défaut
- Toolbar personnalisé dans le layout principal
- Ajout des fichiers d'icône launcher foreground pour les densités HDPI et MDPI

### Fixed
- Résolution des erreurs de compilation liées aux ressources de couleurs
- Correction de la configuration de la barre d'action dans MainActivity
- Correction des ressources pour l'icône launcher

### Changed
- Mise à jour de la configuration du thème pour utiliser un Toolbar personnalisé
- Ajustement de la structure du layout principal

## Modification Log

### 2024-12-27 23:52 UTC
- Refonte complète de la migration pour plus de robustesse
- Ajout de la construction dynamique des requêtes de migration
- Ajout de valeurs par défaut pour toutes les colonnes

### 2024-12-27 23:45 UTC
- Correction de la migration de la base de données
- Ajout d'une gestion flexible de la colonne taskType
- Mise à jour du CHANGELOG

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

## Contributing
Lors de l'ajout de modifications au changelog, veuillez suivre ces directives :
- Utilisez [Semantic Versioning](https://semver.org/)
- Regroupez les modifications par catégories : Added, Changed, Deprecated, Removed, Fixed, Security
- Incluez la date et l'heure précises, ainsi qu'une description claire