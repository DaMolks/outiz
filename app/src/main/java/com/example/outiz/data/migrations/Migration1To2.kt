package com.example.outiz.data.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Ajout du champ status à la table Report
        database.execSQL(
            "ALTER TABLE reports ADD COLUMN status TEXT NOT NULL DEFAULT 'DRAFT'"
        )
    }
}