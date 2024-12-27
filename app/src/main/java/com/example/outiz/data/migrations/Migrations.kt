package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Transformation de la structure de time_entries
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Créer une nouvelle table avec la structure exacte
            db.execSQL("""
                CREATE TABLE time_entries_new (
                    `date` INTEGER NOT NULL,
                    `duration` INTEGER NOT NULL,
                    `taskType` TEXT NOT NULL DEFAULT 'STANDARD',
                    `reportId` TEXT NOT NULL,
                    `description` TEXT NOT NULL,
                    `startTime` TEXT NOT NULL,
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `endTime` TEXT NOT NULL
                )
            """)

            // Vérifier si la colonne taskType existe
            val cursor = db.query("SELECT * FROM time_entries LIMIT 1")
            val hasTaskType = cursor.getColumnIndex("taskType") != -1
            cursor.close()

            // Copier les données en respectant l'ordre exact
            if (hasTaskType) {
                db.execSQL("""
                    INSERT INTO time_entries_new (
                        `date`, `duration`, `taskType`, `reportId`, 
                        `description`, `startTime`, `id`, `endTime`
                    ) SELECT 
                        `date`, `duration`, `taskType`, `reportId`, 
                        `description`, `startTime`, `id`, `endTime` 
                    FROM time_entries
                """)
            } else {
                db.execSQL("""
                    INSERT INTO time_entries_new (
                        `date`, `duration`, `reportId`, 
                        `description`, `startTime`, `id`, `endTime`
                    ) SELECT 
                        `date`, `duration`, `reportId`, 
                        `description`, `startTime`, `id`, `endTime` 
                    FROM time_entries
                """)
            }

            // Supprimer l'ancienne table
            db.execSQL("DROP TABLE time_entries")

            // Renommer la nouvelle table
            db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer l'index
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }
}