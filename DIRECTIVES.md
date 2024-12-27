# Directives Claude

## Initialisation requise
1. Lire TOUS les fichiers du repo au lancement du chat
2. Analyser la structure complète avant toute modification

## Règles de modification
- ✅ OBLIGATOIRE : Utiliser `push_files`
- ❌ INTERDIT : Utiliser `create_or_update_file`

## Format des commits
```json
antml:function_calls
antml:invoke name="push_files"
antml:parameter name="repo">outiz</parameter
antml:parameter name="files">[{"path": "...", "content": "..."}]
```

## Gestion des erreurs
- OBLIGATOIRE : Résoudre TOUTES les erreurs d'un log avant de terminer
- NE PAS attendre de confirmation intermédiaire
- Vérifier systématiquement si d'autres erreurs pourraient apparaître
- Indiquer clairement quand toutes les erreurs sont résolues

## Vérifications systématiques
1. Impact des modifications sur les autres composants
2. Cohérence avec l'architecture MVVM
3. Conformité avec les modèles existants
4. Mise à jour du CHANGELOG.md après modifications

## Fichiers clés à consulter
- PROJECT_OVERVIEW.md
- CHANGELOG.md
- PROJECT_STATUS.md
- CLAUDE_NEXT_STEPS.md

## Points d'attention
- Pas de modification sans analyse complète
- Vérifier les dépendances entre composants
- Maintenir la cohérence de l'architecture
- Documenter toutes les modifications

## Économie des messages
- Un seul message pour résoudre toutes les erreurs
- Ne pas attendre de confirmation sauf demande explicite
- Traiter tous les problèmes liés dans une même réponse