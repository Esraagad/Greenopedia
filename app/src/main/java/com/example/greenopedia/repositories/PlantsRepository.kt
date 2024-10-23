package com.example.greenopedia.repositories

import com.example.greenopedia.data.local.PlantsDao
import com.example.greenopedia.data.remote.TrefleAPI
import com.example.greenopedia.data.remote.responses.PlantsResponse
import retrofit2.Response
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val plantsDao: PlantsDao,
    private val trefleAPI: TrefleAPI
) : PlantsRepositoryImpl {

    override suspend fun getAllPlants(pageNum: Int): Response<PlantsResponse> =
        trefleAPI.getAllPlants(pageNumber = pageNum)

    override suspend fun getPlantsByFilter(
        filterId: String,
        pageNum: Int
    ): Response<PlantsResponse> =
        trefleAPI.getAllPlantsBy(filter = filterId, pageNumber = pageNum)


}