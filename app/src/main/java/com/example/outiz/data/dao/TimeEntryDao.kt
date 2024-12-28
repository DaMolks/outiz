package com.example.outiz.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.outiz.models.TimeEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timeEntry: TimeEntry): Long

    @Query("SELECT * FROM time_entries WHERE reportId = :reportId")
    fun getTimeEntriesForReport(reportId: Long): Flow<List<TimeEntry>>

    @Delete
    suspend fun delete(timeEntry: TimeEntry)

    @Query("DELETE FROM time_entries WHERE reportId = :reportId")
    suspend fun deleteTimeEntriesForReport(reportId: Long)
}