package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Transformation de la structure de time_entries
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Vérifier la structure actuelle de la table
            val cursor = db.query("SELECT * FROM time_entries LIMIT 1")
            val columns = cursor.columnNames.toList()
            cursor.close()

            // Créer une nouvelle table avec tous les champs requis
            db.execSQL("""
                CREATE TABLE time_entries_new (
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    `date` INTEGER NOT NULL DEFAULT 0,
                    `startTime` TEXT NOT NULL DEFAULT '',
                    `endTime` TEXT NOT NULL DEFAULT '',
                    `duration` INTEGER NOT NULL DEFAULT 0,
                    `description` TEXT NOT NULL DEFAULT '',
                    `taskType` TEXT NOT NULL DEFAULT 'STANDARD',
                    `reportId` TEXT NOT NULL DEFAULT ''
                )
            """)

            // Construire dynamiquement la requête INSERT en fonction des colonnes existantes
            val existingColumns = columns.filter { it != "id" }.joinToString(", ") { "`$it`" }
            
            // Si aucune colonne existante, insérer juste l'ID
            if (existingColumns.isEmpty()) {
                db.execSQL("""
                    INSERT INTO time_entries_new (`id`)
                    SELECT `id` FROM time_entries
                """)
            } else {
                db.execSQL("""
                    INSERT INTO time_entries_new (`id`, $existingColumns)
                    SELECT `id`, $existingColumns FROM time_entries
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