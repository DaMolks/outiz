package com.example.outiz.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.outiz.data.repository.ReportRepository
import com.example.outiz.models.Report
import com.example.outiz.ui.viewmodel.ReportViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

@ExperimentalCoroutinesApi
class ReportViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var repository: ReportRepository

    private lateinit var viewModel: ReportViewModel

    private val testReport = Report(
        id = 1L,
        siteName = "Test Site",
        description = "Test Description",
        createdAt = Date(),
        updatedAt = Date()
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ReportViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadReport should update currentReport when report exists`() = runTest {
        // Given
        `when`(repository.getReportById(1L)).thenReturn(flowOf(testReport))

        // When
        viewModel.loadReport(1L)

        // Then
        assert(viewModel.currentReport.value == testReport)
    }

    @Test
    fun `loadReport should set currentReport to null when report doesn't exist`() = runTest {
        // Given
        `when`(repository.getReportById(1L)).thenReturn(flowOf(null))

        // When
        viewModel.loadReport(1L)

        // Then
        assert(viewModel.currentReport.value == null)
    }
}