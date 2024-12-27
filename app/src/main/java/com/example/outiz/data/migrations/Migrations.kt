package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDateTime

object Migrations {
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Créer une table temporaire pour la migration
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS time_entries_new (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `reportId` TEXT NOT NULL,
                    `date` INTEGER NOT NULL,
                    `duration` INTEGER NOT NULL,
                    `startTime` TEXT NOT NULL,
                    `endTime` TEXT NOT NULL,
                    `description` TEXT NOT NULL,
                    `taskType` TEXT NOT NULL
                )
            """)

            // Vérifier l'existence de la table source
            val tableExists = try {
                db.query("SELECT name FROM sqlite_master WHERE type='table' AND name='time_entries'").use { cursor ->
                    cursor.moveToFirst() && cursor.count > 0
                }
            } catch (e: Exception) {
                false
            }

            if (tableExists) {
                try {
                    // Vérifier les colonnes existantes
                    val cursor = db.query("SELECT * FROM time_entries LIMIT 1")
                    val columns = cursor.columnNames.toSet()
                    cursor.close()

                    // Préparer les colonnes pour la requête INSERT
                    val selectColumns = mutableListOf<String>()
                    val now = System.currentTimeMillis()
                    
                    // id est toujours présent
                    selectColumns.add("id")
                    
                    // Gérer chaque colonne avec une valeur par défaut appropriée
                    selectColumns.add(if ("reportId" in columns) "reportId" else "''")
                    selectColumns.add(if ("date" in columns) "date" else "$now")
                    selectColumns.add(if ("duration" in columns) "duration" else "0")
                    selectColumns.add(if ("startTime" in columns) "startTime" else "'${LocalDateTime.now()}'")
                    selectColumns.add(if ("endTime" in columns) "endTime" else "'${LocalDateTime.now()}'")
                    selectColumns.add(if ("description" in columns) "description" else "''")
                    selectColumns.add(if ("taskType" in columns) "taskType" else "'STANDARD'")

                    // Construire et exécuter la requête de copie
                    val selectClause = selectColumns.joinToString(", ")
                    db.execSQL("""
                        INSERT INTO time_entries_new (
                            id, reportId, date, duration, startTime, endTime, description, taskType
                        )
                        SELECT $selectClause
                        FROM time_entries
                    """)
                } catch (e: Exception) {
                    // En cas d'erreur, insérer seulement les IDs avec des valeurs par défaut
                    db.execSQL("""
                        INSERT INTO time_entries_new (id, reportId, date, duration, startTime, endTime, description, taskType)
                        SELECT 
                            id,
                            '' as reportId,
                            ${System.currentTimeMillis()} as date,
                            0 as duration,
                            '${LocalDateTime.now()}' as startTime,
                            '${LocalDateTime.now()}' as endTime,
                            '' as description,
                            'STANDARD' as taskType
                        FROM time_entries
                    """)
                }
            }

            // Supprimer l'ancienne table et renommer la nouvelle
            db.execSQL("DROP TABLE IF EXISTS time_entries")
            db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer les index nécessaires
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
            
            // Vérifier l'intégrité après la migration
            try {
                db.query("SELECT * FROM time_entries LIMIT 1")
            } catch (e: Exception) {
                // Si la vérification échoue, créer une table vide avec la structure correcte
                db.execSQL("DROP TABLE IF EXISTS time_entries")
                db.execSQL("""
                    CREATE TABLE time_entries (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `reportId` TEXT NOT NULL,
                        `date` INTEGER NOT NULL,
                        `duration` INTEGER NOT NULL,
                        `startTime` TEXT NOT NULL,
                        `endTime` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        `taskType` TEXT NOT NULL
                    )
                """)
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
            }
        }
    }
}