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
    version = 2,
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

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Recréer la table time_entries avec la nouvelle structure
                database.execSQL("DROP TABLE IF EXISTS time_entries")
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
                        FOREIGN KEY(reportId) REFERENCES reports(id) ON DELETE CASCADE,
                        FOREIGN KEY(technicianId) REFERENCES technicians(id) ON DELETE CASCADE
                    )
                """)
                database.execSQL("CREATE INDEX index_time_entries_reportId ON time_entries(reportId)")
                database.execSQL("CREATE INDEX index_time_entries_technicianId ON time_entries(technicianId)")
            }
        }

        fun getDatabase(context: Context): OutizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OutizDatabase::class.java,
                    "outiz_database"
                ).addMigrations(MIGRATION_1_2)
                 .fallbackToDestructiveMigration() // Optionnel : réinitialise la base si la migration échoue
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}