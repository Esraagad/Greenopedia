package com.example.greenopedia.data.remote

import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrefleAPI {

    @GET("v1/plants")
    suspend fun getAllPlants(
        @Query("token") apiToken: String = Constants.API_TOKEN,
        @Query("page") pageNumber: Int = 1
    ): Response<PlantsResponse>

    @GET("v1/distributions/{filter}/plants")
    suspend fun getAllPlantsBy(
        @Query("token") apiToken: String = Constants.API_TOKEN,
        @Query("page") pageNumber: Int = 1,
        @Path("filter") filter: String
    ): Response<PlantsResponse>
}