package com.example.outiz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.Site
import com.example.outiz.models.TimeEntry

@Database(
    entities = [
        Report::class,
        Site::class,
        TimeEntry::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class OutizDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao
    abstract fun siteDao(): SiteDao
    abstract fun timeEntryDao(): TimeEntryDao

    companion object {
        private const val DATABASE_NAME = "outiz.db"

        @Volatile
        private var instance: OutizDatabase? = null

        fun getInstance(context: Context): OutizDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): OutizDatabase {
            return Room.databaseBuilder(
                context,
                OutizDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}