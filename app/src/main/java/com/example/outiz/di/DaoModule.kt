package com.example.outiz.di

import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.ReportDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideReportDao(database: OutizDatabase): ReportDao {
        return database.reportDao()
    }
}