package com.example.greenopedia.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenopedia.data.remote.responses.Data

@Dao
interface PlantsDao {

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plantsList: List<Data>)

    @Query("SELECT COUNT(*) FROM plants")
    suspend fun getCount(): Int

    @Query("SELECT * FROM plants WHERE zone= :zone")
    suspend fun getPlantsByFilter(zone: String): List<Data>
}