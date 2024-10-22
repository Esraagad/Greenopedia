package com.example.greenopedia.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenopedia.data.remote.responses.Data

@Dao
interface PlantsDao {

    @Query("SELECT * FROM plants")
    fun getAllPlants(): LiveData<List<Data>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlants(plantsList: List<Data>)
}