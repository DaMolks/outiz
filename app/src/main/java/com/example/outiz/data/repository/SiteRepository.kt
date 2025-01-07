package com.example.outiz.data.repository

import androidx.lifecycle.LiveData
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.models.Site
import javax.inject.Inject

class SiteRepository @Inject constructor(
    private val siteDao: SiteDao
) {
    val allSites: LiveData<List<Site>> = siteDao.getAllSites()

    suspend fun insert(site: Site) = siteDao.insertSite(site)

    suspend fun update(site: Site) = siteDao.updateSite(site)

    suspend fun delete(site: Site) = siteDao.deleteSite(site)

    suspend fun getSiteById(id: Long): Site? = siteDao.getSiteById(id)
}