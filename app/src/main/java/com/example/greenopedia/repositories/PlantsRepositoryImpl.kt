package com.example.greenopedia.repositories

import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.utils.Resource

interface PlantsRepositoryImpl {

    suspend fun fetchAllPlants(pageNum: Int): Resource<PlantsResponse>

    suspend fun fetchPlantsByFilter(filterId: String, pageNum: Int): Resource<PlantsResponse>

    suspend fun loadLocalPlants(): Resource<PlantsResponse>

    suspend fun loadLocalPlantsByFilter(zone: String): Resource<PlantsResponse>

    suspend fun insertAllPlants(plants: List<Data>)

    suspend fun getPlantsCount(): Int
}