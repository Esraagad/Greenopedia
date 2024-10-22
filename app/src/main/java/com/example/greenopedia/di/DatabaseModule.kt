package com.example.greenopedia.di

import android.content.Context
import androidx.room.Room
import com.example.greenopedia.data.local.PlantsDao
import com.example.greenopedia.data.local.PlantsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providePlantsDatabase(@ApplicationContext context: Context): PlantsDatabase {
        return Room.databaseBuilder(context, PlantsDatabase::class.java, "Plants_DB.db").build()
    }

    @Singleton
    @Provides
    fun providesPlantsDao(plantsDatabase: PlantsDatabase): PlantsDao {
        return plantsDatabase.getPlantsDao()
    }
}