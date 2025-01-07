package com.example.outiz.di

import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.data.repository.SiteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideReportRepository(reportDao: ReportDao): ReportRepository {
        return ReportRepository(reportDao)
    }

    @Provides
    @Singleton
    fun provideSiteRepository(siteDao: SiteDao): SiteRepository {
        return SiteRepository(siteDao)
    }
}