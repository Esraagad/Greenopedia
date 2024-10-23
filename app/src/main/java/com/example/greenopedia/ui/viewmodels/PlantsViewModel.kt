package com.example.greenopedia.ui.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.greenopedia.GreenopediaApplication
import com.example.greenopedia.data.remote.responses.PlantsResponse
import com.example.greenopedia.repositories.PlantsRepositoryImpl
import com.example.greenopedia.utils.ErrorMessages
import com.example.greenopedia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val plantsRepository: PlantsRepositoryImpl,
    app: Application
) : AndroidViewModel(app) {
    private var _plants = MutableLiveData<Resource<PlantsResponse>?>()
    val plants: LiveData<Resource<PlantsResponse>?> = _plants
    private var plantsResponse: PlantsResponse? = null
    var plantsPageNum = 1

    init {
        getAllPlants()
    }

    fun getAllPlants() = viewModelScope.launch {
        _plants.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = plantsRepository.getAllPlants(plantsPageNum)
                _plants.postValue(handlePlantsResponse(response))
            } else {
                _plants.postValue(Resource.Error(ErrorMessages.NOInternetConnection, null))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> _plants.postValue(
                    Resource.Error(
                        ErrorMessages.NetworkFailur,
                        null
                    )
                )

                else -> _plants.postValue(Resource.Error(ErrorMessages.ConversionError, null))
            }
        }
    }

    private fun handlePlantsResponse(response: Response<PlantsResponse>): Resource<PlantsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                plantsPageNum++
                if (plantsResponse == null) {
                    plantsResponse = resultResponse
                } else {
                    val newPlants = resultResponse.plantsList
                    plantsResponse?.plantsList?.addAll(newPlants)
                }
                return Resource.Success(plantsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message(), null)
    }

    fun resetData(){
        //reset all livedata
        plantsPageNum = 1
        plantsResponse = null
        _plants.value = null
    }

    fun getAllPlantsByFilter(filterId: String) = viewModelScope.launch {

        _plants.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = plantsRepository.getPlantsByFilter(filterId, plantsPageNum)
                _plants.postValue(handlePlantsResponse(response))
            } else {
                _plants.postValue(Resource.Error(ErrorMessages.NOInternetConnection, null))
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> _plants.postValue(
                    Resource.Error(
                        ErrorMessages.NetworkFailur,
                        null
                    )
                )

                else -> _plants.postValue(Resource.Error(ErrorMessages.ConversionError, null))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<GreenopediaApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}