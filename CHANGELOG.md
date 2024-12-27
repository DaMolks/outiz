# Changelog

## [Unreleased]

### Fixed
- Refonte majeure de la migration de base de données :
  - Gestion complète des conversions de types Date et LocalDateTime
  - Vérification approfondie de l'intégrité des données
  - Fallback automatique en cas d'erreur
  - Validation post-migration
- Utilisation correcte des valeurs par défaut pour toutes les colonnes

### Added
- Ressources de couleurs Material Design Light
- Configuration de thème sans barre d'action par défaut
- Toolbar personnalisé dans le layout principal
- Ajout des fichiers d'icône launcher foreground pour les densités HDPI et MDPI

### Changed
- Mise à jour de la configuration du thème pour utiliser un Toolbar personnalisé
- Ajustement de la structure du layout principal

## Modification Log

### 2024-12-28 00:10 UTC
- Refonte majeure de la migration pour gérer correctement les types de données
- Amélioration de la robustesse de la migration
- Ajout de vérifications d'intégrité
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