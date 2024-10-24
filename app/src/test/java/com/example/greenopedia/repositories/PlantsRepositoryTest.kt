package com.example.greenopedia.repositories

import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.utils.Resource


class PlantsRepositoryTest: PlantsRepositoryImpl {
    override suspend fun fetchAllPlants(pageNum: Int): Resource<PlantsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchPlantsByFilter(
        filterId: String,
        pageNum: Int
    ): Resource<PlantsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun loadLocalPlants(): Resource<PlantsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun loadLocalPlantsByFilter(zone: String): Resource<PlantsResponse> {
        TODO("Not yet implemented")
    }
}