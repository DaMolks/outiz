package com.example.outiz.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.outiz.data.dao.*
import com.example.outiz.data.migrations.MIGRATION_1_2
import com.example.outiz.models.*
import timber.log.Timber

@Database(
    entities = [
        Report::class,
        Site::class,
        Technician::class,
        TimeEntry::class
    ],
    version = 2,
    exportSchema = true,
    autoBackup = true
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
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.d("Database created")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Timber.d("Database opened")
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
                .enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = instance
                instance
            }
        }
    }
}