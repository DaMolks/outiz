# Changelog

## [Unreleased]

### Added
- Ressources de couleurs Material Design Light
- Configuration de thème sans barre d'action par défaut
- Toolbar personnalisé dans le layout principal

### Fixed
- Résolution des erreurs de compilation liées aux ressources de couleurs
- Correction de la configuration de la barre d'action dans MainActivity

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

## Contributing
Lors de l'ajout de modifications au changelog, veuillez suivre ces directives :
- Utilisez [Semantic Versioning](https://semver.org/)
- Regroupez les modifications par catégories : Added, Changed, Deprecated, Removed, Fixed, Security
- Incluez la date et l'heure précises, ainsi qu'une description claire
