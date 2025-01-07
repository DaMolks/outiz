package com.example.outiz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.data.migrations.MIGRATION_1_2
import com.example.outiz.models.Report
import com.example.outiz.models.Site
import com.example.outiz.models.Technician
import com.example.outiz.models.TimeEntry

@Database(
    entities = [
        Report::class,
        Site::class,
        Technician::class,
        TimeEntry::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class OutizDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
    abstract fun siteDao(): SiteDao
    abstract fun technicianDao(): TechnicianDao
    abstract fun timeEntryDao(): TimeEntryDao

    companion object {
        @Volatile
        private var INSTANCE: OutizDatabase? = null

        fun getDatabase(context: Context): OutizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OutizDatabase::class.java,
                    "outiz_database"
                )
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}