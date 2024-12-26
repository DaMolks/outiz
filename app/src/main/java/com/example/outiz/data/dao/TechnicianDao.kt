package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Technician
import kotlinx.coroutines.flow.Flow

@Dao
interface TechnicianDao {
    @Query("SELECT * FROM technicians")
    fun getAllTechnicians(): Flow<List<Technician>>

    @Query("SELECT * FROM technicians WHERE id = :id")
    suspend fun getTechnicianById(id: String): Technician?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnician(technician: Technician)

    @Update
    suspend fun updateTechnician(technician: Technician)

    @Delete
    suspend fun deleteTechnician(technician: Technician)
}
