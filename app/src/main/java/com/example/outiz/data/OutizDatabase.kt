package com.example.outiz.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import com.example.outiz.utils.Constants

@Database(
    entities = [Report::class, TimeEntry::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class OutizDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
    abstract fun timeEntryDao(): TimeEntryDao

    companion object {
        @Volatile
        private var INSTANCE: OutizDatabase? = null

        fun getDatabase(context: Context): OutizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OutizDatabase::class.java,
                    Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}