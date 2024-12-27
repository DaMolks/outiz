package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Transformation de la structure de time_entries
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // Récupérer toutes les données existantes
            val cursor = db.query("SELECT * FROM time_entries")
            
            // Créer une nouvelle table avec la structure exacte
            db.execSQL("""
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

            // Parcourir et insérer les données
            if (cursor.moveToFirst()) {
                val columns = cursor.columnNames
                while (!cursor.isAfterLast) {
                    // Extraire les valeurs des colonnes
                    val date = cursor.getLong(columns.indexOf("date"))
                    val duration = cursor.getInt(columns.indexOf("interventionDuration"))
                    val reportId = cursor.getString(columns.indexOf("reportId"))
                    val startTime = cursor.getString(columns.indexOf("arrivalTime"))
                    val endTime = cursor.getString(columns.indexOf("departureTime"))
                    val description = cursor.getString(columns.indexOf("technicianFirstName")) + " " + 
                                      cursor.getString(columns.indexOf("technicianLastName"))

                    // Insérer dans la nouvelle table
                    db.execSQL("""
                        INSERT INTO time_entries_new (
                            `date`, `duration`, `taskType`, `reportId`, `description`, 
                            `startTime`, `endTime`
                        ) VALUES (?, ?, ?, ?, ?, ?, ?)
                    """, arrayOf(date, duration, "Intervention", reportId, description, startTime, endTime))

                    cursor.moveToNext()
                }
            }

            // Fermer le curseur
            cursor.close()

            // Supprimer l'ancienne table
            db.execSQL("DROP TABLE time_entries")

            // Renommer la nouvelle table
            db.execSQL("ALTER TABLE time_entries_new RENAME TO time_entries")

            // Recréer l'index
            db.execSQL("CREATE INDEX IF NOT EXISTS `index_time_entries_reportId` ON `time_entries` (`reportId`)")
        }
    }
}