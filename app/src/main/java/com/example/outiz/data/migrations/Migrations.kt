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
                    `date` INTEGER NOT NULL,
                    `duration` INTEGER NOT NULL,
                    `taskType` TEXT NOT NULL,
                    `reportId` TEXT NOT NULL,
                    `description` TEXT NOT NULL,
                    `startTime` TEXT NOT NULL,
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `endTime` TEXT NOT NULL
                )
            """)

            // Copier les données en convertissant si nécessaire
            database.execSQL("""
                INSERT INTO time_entries_new (
                    `date`, `duration`, `taskType`, `reportId`, 
                    `description`, `startTime`, `id`, `endTime`
                ) 
                SELECT 
                    `date`, 
                    IFNULL(`interventionDuration`, 0) AS `duration`,
                    'Intervention' AS `taskType`,
                    `reportId`, 
                    IFNULL(`technicianFirstName` || ' ' || IFNULL(`technicianLastName`, ''), '') AS `description`,
                    datetime(`arrivalTime` / 1000, 'unixepoch') AS `startTime`,
                    IFNULL(id, (ABS(RANDOM()) % 1000000)) AS `id`,
                    datetime(`departureTime` / 1000, 'unixepoch') AS `endTime`
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