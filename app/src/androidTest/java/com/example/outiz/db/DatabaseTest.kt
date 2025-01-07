package com.example.outiz.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.outiz.data.OutizDatabase
import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.SiteDao
import com.example.outiz.models.Report
import com.example.outiz.models.Site
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var db: OutizDatabase
    private lateinit var reportDao: ReportDao
    private lateinit var siteDao: SiteDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, OutizDatabase::class.java
        ).build()
        reportDao = db.reportDao()
        siteDao = db.siteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndReadSite() = runBlocking {
        val site = Site(
            name = "Test Site",
            code = "TST1",
            address = "123 Test St",
            client = "Test Client"
        )
        siteDao.insertSite(site)

        val allSites = siteDao.getAllSites().value
        assert(allSites?.isNotEmpty() == true)
        assert(allSites?.get(0)?.name == "Test Site")
    }

    @Test
    fun insertAndReadReport() = runBlocking {
        val site = Site(
            name = "Test Site",
            code = "TST1",
            address = "123 Test St",
            client = "Test Client"
        )
        val siteId = siteDao.insertSite(site)

        val report = Report(
            siteName = "Test Site",
            siteId = siteId,
            technicianId = 1,
            description = "Test Description",
            createdAt = Date()
        )
        reportDao.insertReport(report)

        val allReports = reportDao.getAllReports().value
        assert(allReports?.isNotEmpty() == true)
        assert(allReports?.get(0)?.description == "Test Description")
    }
}