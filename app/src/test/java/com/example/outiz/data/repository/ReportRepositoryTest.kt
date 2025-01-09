package com.example.outiz.data.repository

import com.example.outiz.data.dao.ReportDao
import com.example.outiz.data.dao.TimeEntryDao
import com.example.outiz.models.Report
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

class ReportRepositoryTest {

    @Mock
    private lateinit var reportDao: ReportDao

    @Mock
    private lateinit var timeEntryDao: TimeEntryDao

    private lateinit var repository: ReportRepository

    private val testReport = Report(
        id = 1L,
        siteName = "Test Site",
        description = "Test Description",
        createdAt = Date(),
        updatedAt = Date()
    )

    private val testTimeEntry = TimeEntry(
        id = 1L,
        reportId = 1L,
        duration = 60,
        description = "Test Entry",
        startTime = Date()
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ReportRepository(reportDao, timeEntryDao)
    }

    @Test
    fun `getAllReports returns flow of reports`() = runTest {
        // Given
        val reports = listOf(testReport)
        `when`(reportDao.getAllReports()).thenReturn(flowOf(reports))

        // When
        val result = repository.getAllReports().first()

        // Then
        assert(result == reports)
    }

    @Test
    fun `getTimeEntriesForReport returns flow of time entries`() = runTest {
        // Given
        val timeEntries = listOf(testTimeEntry)
        `when`(timeEntryDao.getTimeEntriesForReport(1L)).thenReturn(flowOf(timeEntries))

        // When
        val result = repository.getTimeEntriesForReport(1L).first()

        // Then
        assert(result == timeEntries)
    }
}