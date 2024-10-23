package com.example.greenopedia.repositories

import com.example.greenopedia.data.remote.responses.PlantsResponse
import retrofit2.Response

interface PlantsRepositoryImpl {

    suspend fun getAllPlants(pageNum: Int): Response<PlantsResponse>

    suspend fun getPlantsByFilter(filterId: String, pageNum: Int): Response<PlantsResponse>

}