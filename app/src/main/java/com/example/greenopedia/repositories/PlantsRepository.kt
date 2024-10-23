package com.example.greenopedia.repositories

import com.example.greenopedia.data.local.PlantsDao
import com.example.greenopedia.data.remote.TrefleAPI
import com.example.greenopedia.data.remote.responses.Data
import com.example.greenopedia.data.remote.responses.Meta
import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.utils.ErrorMessages
import com.example.greenopedia.utils.Resource
import javax.inject.Inject

class PlantsRepository @Inject constructor(
    private val plantsDao: PlantsDao,
    private val trefleAPI: TrefleAPI
) : PlantsRepositoryImpl {

    override suspend fun fetchAllPlants(pageNum: Int): Resource<PlantsResponse> {
        return try {
            val response =
                trefleAPI.getAllPlants(pageNumber = pageNum)
            if (response.isSuccessful) {
                response.body()?.let {
                    plantsDao.insertAllPlants(it.plantsList)
                    return@let Resource.Success(it)
                } ?: return Resource.Error(response.message(), null)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(ErrorMessages.NO_INTERNET_CONNECTION)
        }
    }

    override suspend fun fetchPlantsByFilter(
        filterId: String,
        pageNum: Int
    ): Resource<PlantsResponse> {
        return try {
            val response =
                trefleAPI.getAllPlantsBy(filter = filterId, pageNumber = pageNum)
            if (response.isSuccessful) {
                response.body()?.let {
                    it.plantsList.forEach { plant ->
                        plant.zone = filterId
                    }
                    plantsDao.insertAllPlants(it.plantsList)
                    return@let Resource.Success(it)
                } ?: return Resource.Error(response.message(), null)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(ErrorMessages.NO_INTERNET_CONNECTION)
        }
    }

    override suspend fun loadLocalPlants(): Resource<PlantsResponse> {
        var localsResponse = PlantsResponse(
            mutableListOf(),
            null,
            Meta(0)
        )
        val count = getPlantsCount()
        val plants = plantsDao.getAllPlants()
        plants.let {
            localsResponse.plantsList = plants as MutableList<Data>
            localsResponse.links = null
            localsResponse.meta = Meta(count)
        }
        return Resource.Success(localsResponse)
    }

    override suspend fun loadLocalPlantsByFilter(zone:String): Resource<PlantsResponse> {
        var localsResponse = PlantsResponse(
            mutableListOf(),
            null,
            Meta(0)
        )
        val count = getPlantsCount()
        val plants = plantsDao.getPlantsByFilter(zone)
        plants.let {
            localsResponse.plantsList = plants as MutableList<Data>
            localsResponse.links = null
            localsResponse.meta = Meta(count)
        }
        return Resource.Success(localsResponse)    }

    override suspend fun insertAllPlants(plants: List<Data>) =
        plantsDao.insertAllPlants(plants)

    override suspend fun getPlantsCount(): Int = plantsDao.getCount()

}