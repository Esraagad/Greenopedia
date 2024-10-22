package com.example.greenopedia.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenopedia.data.remote.responses.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantsDao {

    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plantsList: List<Data>)
}