package com.example.outiz.di

import android.content.Context
import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TechnicianDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.utils.AppPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): OutizDatabase {
        return OutizDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideAppPreferenceManager(@ApplicationContext context: Context): AppPreferenceManager {
        return AppPreferenceManager(context)
    }

    @Provides
    fun provideReportDao(database: OutizDatabase): ReportDao = database.reportDao()

    @Provides
    fun provideSiteDao(database: OutizDatabase): SiteDao = database.siteDao()

    @Provides
    fun provideTechnicianDao(database: OutizDatabase): TechnicianDao = database.technicianDao()

    @Provides
    fun provideTimeEntryDao(database: OutizDatabase): TimeEntryDao = database.timeEntryDao()
}