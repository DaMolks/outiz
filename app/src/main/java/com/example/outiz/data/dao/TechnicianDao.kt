package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Technician
import kotlinx.coroutines.flow.Flow

@Dao
interface TechnicianDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(technician: Technician): Long

    @Query("SELECT * FROM technicians")
    fun getAllTechnicians(): Flow<List<Technician>>

    @Query("SELECT * FROM technicians WHERE id = :id")
    fun getTechnicianById(id: Long): Flow<Technician?>

    @Query("SELECT * FROM technicians WHERE employeeId = :employeeId")
    suspend fun getTechnicianByEmployeeId(employeeId: String): Technician?

    @Delete
    suspend fun delete(technician: Technician)

    @Query("DELETE FROM technicians")
    suspend fun deleteAll()
}