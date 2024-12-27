package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.TimeEntry

@Dao
interface TimeEntryDao {
    @Insert
    suspend fun insert(timeEntry: TimeEntry)

    @Update
    suspend fun update(timeEntry: TimeEntry)

    @Delete
    suspend fun delete(timeEntry: TimeEntry)

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    suspend fun getEntriesForReport(reportId: Long): List<TimeEntry>

    @Query("DELETE FROM time_entries WHERE reportId = :reportId")
    suspend fun deleteAllForReport(reportId: Long)
}