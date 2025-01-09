package com.example.outiz.di

import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.data.repository.SiteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideReportRepository(
        reportDao: ReportDao,
        timeEntryDao: TimeEntryDao
    ): ReportRepository {
        return ReportRepository(reportDao, timeEntryDao)
    }

    @Provides
    fun provideSiteRepository(siteDao: SiteDao): SiteRepository {
        return SiteRepository(siteDao)
    }
}