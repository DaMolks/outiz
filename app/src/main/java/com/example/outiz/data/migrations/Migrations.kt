package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 1 à 2 : Ajout de la table TimeEntry
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Créer la table time_entries
            database.execSQL("""
                CREATE TABLE IF NOT EXISTS `time_entries` (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `reportId` TEXT NOT NULL,
                    `date` INTEGER,
                    `duration` INTEGER NOT NULL,
                    `startTime` TEXT,
                    `endTime` TEXT,
                    `description` TEXT NOT NULL,
                    `taskType` TEXT NOT NULL
                )
            """)

            // Ajouter un index pour améliorer les performances de requête
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }

    // Migration de la version 2 à 3 : Modification potentielle du schéma
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Exemple de migration : ajouter une colonne
            // database.execSQL("ALTER TABLE reports ADD COLUMN new_column TEXT")
        }
    }
}