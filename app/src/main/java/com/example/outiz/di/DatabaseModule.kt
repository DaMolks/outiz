package com.example.outiz.di

import android.content.Context
import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OutizDatabase {
        return OutizDatabase.getDatabase(context)
    }

    @Provides
    fun provideTechnicianDao(database: OutizDatabase): TechnicianDao {
        return database.technicianDao()
    }

    @Provides
    fun provideSiteDao(database: OutizDatabase): SiteDao {
        return database.siteDao()
    }

    @Provides
    fun provideReportDao(database: OutizDatabase): ReportDao {
        return database.reportDao()
    }

    @Provides
    fun provideTimeEntryDao(database: OutizDatabase): TimeEntryDao {
        return database.timeEntryDao()
    }
}