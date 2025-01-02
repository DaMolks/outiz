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

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId ORDER BY startTime ASC")
    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>>

    @Query("DELETE FROM time_entries WHERE reportId = :reportId")
    suspend fun deleteAllForReport(reportId: Long)
}