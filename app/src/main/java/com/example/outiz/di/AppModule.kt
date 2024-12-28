package com.example.outiz.di

import android.content.Context
import androidx.room.Room
import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.utils.AppPreferenceManager

class AppModule(private val context: Context) {
    val database: OutizDatabase by lazy {
        Room.databaseBuilder(
            context.applicationContext,
            OutizDatabase::class.java,
            "outiz_database"
        ).fallbackToDestructiveMigration().build()
    }

    val reportDao: ReportDao by lazy { database.reportDao() }
    val siteDao: SiteDao by lazy { database.siteDao() }
    val technicianDao: TechnicianDao by lazy { database.technicianDao() }
    val timeEntryDao: TimeEntryDao by lazy { database.timeEntryDao() }

    val preferenceManager: AppPreferenceManager by lazy { 
        AppPreferenceManager(context) 
    }
}