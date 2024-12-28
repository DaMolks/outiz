package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS time_entries_new (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `reportId` TEXT NOT NULL,
                    `date` INTEGER NOT NULL,
                    `duration` INTEGER NOT NULL,
                    `startTime` TEXT NOT NULL,
                    `endTime` TEXT NOT NULL,
                    `description` TEXT NOT NULL,
                    `taskType` TEXT NOT NULL,
                    `technicians` TEXT NOT NULL DEFAULT ''
                )
            """)

            // Sauvegarder les anciennes données
            db.execSQL("CREATE TABLE IF NOT EXISTS time_entries_backup AS SELECT * FROM time_entries")

            try {
                // Copier les données existantes
                db.execSQL("""
                    INSERT INTO time_entries_new (
                        id, reportId, date, duration,
                        startTime, endTime, description, taskType, technicians
                    )
                    SELECT 
                        id, reportId, date, duration,
                        startTime, endTime, description, taskType, ''
                    FROM time_entries
                """)

                // Supprimer l'ancienne table et renommer la nouvelle
                db.execSQL("DROP TABLE time_entries")
                db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

                // Recréer l'index sur reportId
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")

                // Si tout s'est bien passé, supprimer la sauvegarde
                db.execSQL("DROP TABLE IF EXISTS time_entries_backup")

            } catch (e: Exception) {
                // En cas d'erreur, restaurer la sauvegarde
                db.execSQL("DROP TABLE IF EXISTS time_entries")
                db.execSQL("ALTER TABLE time_entries_backup RENAME TO time_entries")
                throw e // Propager l'erreur pour que Room puisse la gérer
            }
        }
    }
}