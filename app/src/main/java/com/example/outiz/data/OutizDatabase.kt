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
                // Ajouter ici les migrations de schéma si nécessaire
                // Par exemple : database.execSQL("ALTER TABLE report ADD COLUMN new_column INTEGER DEFAULT 0")
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