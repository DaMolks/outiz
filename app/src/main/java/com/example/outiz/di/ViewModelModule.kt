package com.example.outiz.di

import com.example.outiz.data.dao.*
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.data.repository.SiteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideReportRepository(
        reportDao: ReportDao,
        timeEntryDao: TimeEntryDao
    ): ReportRepository {
        return ReportRepository(reportDao, timeEntryDao)
    }

    @Provides
    @ViewModelScoped
    fun provideSiteRepository(siteDao: SiteDao): SiteRepository {
        return SiteRepository(siteDao)
    }
}