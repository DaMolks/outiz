package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Créer une nouvelle table avec la structure exacte de l'entité TimeEntry
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

            try {
                // Essayer de copier les données existantes
                db.execSQL("""
                    INSERT INTO time_entries_new (
                        id, reportId, date, duration,
                        startTime, endTime, description, taskType
                    )
                    SELECT 
                        id, reportId, date, duration,
                        startTime, endTime, description, taskType
                    FROM time_entries
                    WHERE EXISTS (SELECT * FROM time_entries)
                """)
            } catch (e: Exception) {
                // En cas d'erreur, effectuer une copie partielle des données
                db.execSQL("INSERT INTO time_entries_new (id) SELECT id FROM time_entries")
                
                // Mettre à jour avec des valeurs par défaut
                db.execSQL("""
                    UPDATE time_entries_new SET
                        reportId = '',
                        date = 0,
                        duration = 0,
                        startTime = '',
                        endTime = '',
                        description = '',
                        taskType = 'STANDARD'
                    WHERE 1=1
                """)
            }

            // Supprimer l'ancienne table et renommer la nouvelle
            db.execSQL("DROP TABLE IF EXISTS time_entries")
            db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer l'index sur reportId
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }
}