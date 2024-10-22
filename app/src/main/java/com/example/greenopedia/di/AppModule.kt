package com.example.greenopedia.di

import com.example.greenopedia.data.local.PlantsDao
import com.example.greenopedia.data.remote.TrefleAPI
import com.example.greenopedia.repositories.PlantsRepository
import com.example.greenopedia.repositories.PlantsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePlantsRepository(
        dao: PlantsDao,
        api: TrefleAPI
    ) = PlantsRepository(dao, api) as PlantsRepositoryImpl
}