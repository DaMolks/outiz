package com.example.outiz.di

import android.content.Context
import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TimeEntryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OutizDatabase {
        return OutizDatabase.getInstance(context)
    }

    @Provides
    fun provideReportDao(database: OutizDatabase): ReportDao {
        return database.reportDao()
    }

    @Provides
    fun provideSiteDao(database: OutizDatabase): SiteDao {
        return database.siteDao()
    }

    @Provides
    fun provideTimeEntryDao(database: OutizDatabase): TimeEntryDao {
        return database.timeEntryDao()
    }
}