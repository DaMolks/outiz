package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Transformation de la structure de time_entries
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Vérifier les colonnes existantes
            val cursor = db.query("PRAGMA table_info(time_entries)")
            val existingColumns = mutableSetOf<String>()

            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex("name")
                while (!cursor.isAfterLast) {
                    existingColumns.add(cursor.getString(nameIndex))
                    cursor.moveToNext()
                }
            }
            cursor.close()

            // Créer une table temporaire
            db.execSQL("""
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
            val copyColumns = listOf(
                "id", "reportId", "date", "duration", 
                "startTime", "endTime", "description", "taskType"
            ).filter { existingColumns.contains(it) }

            val copyQuery = "INSERT INTO time_entries_new (${copyColumns.joinToString(", ")}) " +
                           "SELECT ${copyColumns.joinToString(", ")} FROM time_entries"
            db.execSQL(copyQuery)

            // Supprimer l'ancienne table
            db.execSQL("DROP TABLE time_entries")

            // Renommer la nouvelle table
            db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer l'index
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }
}