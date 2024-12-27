package com.example.outiz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.*

@Database(
    entities = [
        Technician::class,
        Site::class,
        Report::class,
        TimeEntry::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OutizDatabase : RoomDatabase() {
    abstract fun technicianDao(): TechnicianDao
    abstract fun siteDao(): SiteDao
    abstract fun reportDao(): ReportDao
    abstract fun timeEntryDao(): TimeEntryDao

    companion object {
        @Volatile
        private var INSTANCE: OutizDatabase? = null

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Renommer l'ancienne table
                database.execSQL("ALTER TABLE time_entries RENAME TO time_entries_old")

                // Créer la nouvelle table avec la structure actuelle
                database.execSQL("""
                    CREATE TABLE time_entries (
                        id TEXT PRIMARY KEY NOT NULL,
                        reportId TEXT NOT NULL,
                        technicianId TEXT NOT NULL,
                        date INTEGER NOT NULL,
                        duration INTEGER NOT NULL,
                        technicianFirstName TEXT,
                        technicianLastName TEXT,
                        arrivalTime INTEGER,
                        departureTime INTEGER,
                        interventionDuration INTEGER,
                        travelDuration INTEGER,
                        FOREIGN KEY(reportId) REFERENCES reports(id) ON DELETE CASCADE
                    )
                """)

                // Copier les données existantes
                database.execSQL("""
                    INSERT INTO time_entries (
                        id, reportId, technicianId, date, duration,
                        technicianFirstName, technicianLastName,
                        arrivalTime, departureTime,
                        interventionDuration, travelDuration
                    )
                    SELECT id, reportId, technicianId, date, duration,
                           NULL, NULL, NULL, NULL, NULL, NULL
                    FROM time_entries_old
                """)

                // Supprimer l'ancienne table
                database.execSQL("DROP TABLE time_entries_old")

                // Créer les index
                database.execSQL("CREATE INDEX index_time_entries_reportId ON time_entries(reportId)")
            }
        }

        fun getDatabase(context: Context): OutizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OutizDatabase::class.java,
                    "outiz_database"
                ).addMigrations(MIGRATION_2_3)
                 .fallbackToDestructiveMigration() // En cas de problème
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}