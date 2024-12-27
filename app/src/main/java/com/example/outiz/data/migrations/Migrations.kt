package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Liste des migrations pour la base de données Outiz
object Migrations {
    // Migration de la version 3 à 2 : Retour à une version précédente
    val MIGRATION_3_2 = object : Migration(3, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Conserver les tables essentielles
            // Ici on ne fait rien de spécifique car on veut juste passer de 3 à 2
        }
    }

    // Migration de la version 2 à 1 : Retour à la version initiale
    val MIGRATION_2_1 = object : Migration(2, 1) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Aucune action particulière
        }
    }
}