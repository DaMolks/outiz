package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timeEntry: TimeEntry): Long

    @Delete
    suspend fun delete(timeEntry: TimeEntry)

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    suspend fun getTimeEntriesForReport(reportId: String): List<TimeEntry>

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    fun getTimeEntriesForReportFlow(reportId: String): Flow<List<TimeEntry>>
}