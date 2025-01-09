package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeEntryDao {
    @Query("SELECT * FROM time_entries WHERE reportId = :reportId ORDER BY startTime ASC")
    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeEntry(timeEntry: TimeEntry): Long

    @Update
    suspend fun updateTimeEntry(timeEntry: TimeEntry)

    @Delete
    suspend fun deleteTimeEntry(timeEntry: TimeEntry)

    @Query("SELECT SUM(duration) FROM time_entries WHERE reportId = :reportId")
    suspend fun getTotalDurationForReport(reportId: Long): Int?
}