package com.example.outiz.data.dao

import androidx.room.*
import com.example.outiz.models.Site
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteDao {
    @Query("SELECT * FROM sites ORDER BY name ASC")
    fun getAllSites(): Flow<List<Site>>

    @Query("SELECT * FROM sites WHERE id = :id")
    suspend fun getSiteById(id: String): Site?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSite(site: Site)

    @Update
    suspend fun updateSite(site: Site)

    @Delete
    suspend fun deleteSite(site: Site)

    @Query("SELECT * FROM sites WHERE name LIKE '%' || :query || '%' OR codeS LIKE '%' || :query || '%'")
    fun searchSites(query: String): Flow<List<Site>>
}
