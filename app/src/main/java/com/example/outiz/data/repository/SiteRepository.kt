package com.example.outiz.data.repository

import com.example.outiz.data.dao.SiteDao
import com.example.outiz.models.Site
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SiteRepository @Inject constructor(
    private val siteDao: SiteDao
) {
    val allSites: Flow<List<Site>> = siteDao.getAllSites()

    suspend fun insert(site: Site): Long = siteDao.insertSite(site)

    suspend fun update(site: Site) = siteDao.updateSite(site)

    suspend fun delete(site: Site) = siteDao.deleteSite(site)

    fun getSiteById(id: Long): Flow<Site?> = siteDao.getSiteById(id)
}