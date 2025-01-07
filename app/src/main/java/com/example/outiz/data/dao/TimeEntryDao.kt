package com.example.outiz.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeEntryDao {
    @Query("SELECT * FROM time_entries WHERE report_id = :reportId")
    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>>

    @Query("DELETE FROM time_entries WHERE report_id = :reportId")
    suspend fun deleteAllForReport(reportId: Long)
}