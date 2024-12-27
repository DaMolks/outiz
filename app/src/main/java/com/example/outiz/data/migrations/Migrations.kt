package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Transformation de la structure de time_entries
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Créer une nouvelle table temporaire avec la nouvelle structure
            database.execSQL("""
                CREATE TABLE time_entries_new (
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

            // Copier les données en convertissant si nécessaire
            database.execSQL("""
                INSERT INTO time_entries_new (
                    `id`, `reportId`, `date`, `duration`, 
                    `startTime`, `endTime`, `description`, `taskType`
                ) 
                SELECT 
                    IFNULL(id, (ABS(RANDOM()) % 1000000)), 
                    `reportId`, 
                    `date`, 
                    IFNULL(`interventionDuration`, 0) AS `duration`,
                    datetime(`arrivalTime` / 1000, 'unixepoch') AS `startTime`,
                    datetime(`departureTime` / 1000, 'unixepoch') AS `endTime`,
                    IFNULL(`technicianFirstName` || ' ' || IFNULL(`technicianLastName`, ''), '') AS `description`,
                    'Intervention' AS `taskType`
                FROM time_entries
            """)

            // Supprimer l'ancienne table
            database.execSQL("DROP TABLE time_entries")

            // Renommer la nouvelle table
            database.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer l'index
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }
}