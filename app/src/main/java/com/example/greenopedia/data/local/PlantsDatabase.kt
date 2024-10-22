package com.example.greenopedia.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.greenopedia.data.remote.responses.Data

@Database(
    entities = [Data::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class PlantsDatabase : RoomDatabase() {
    abstract fun getPlantsDao(): PlantsDao
}