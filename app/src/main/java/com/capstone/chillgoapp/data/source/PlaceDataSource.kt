package com.capstone.chillgoapp.data.source

import com.capstone.chillgoapp.data.retrofit.PlacesServices
import com.capstone.chillgoapp.data.safeApiCall
import com.capstone.chillgoapp.data.response.BaseResponse
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.RecommendedPlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.ui.common.UiState

interface PlaceDataSource {
    suspend fun getPlaceBasedOnCity(city: String): UiState<BaseResponse<List<PlaceResponse>>>
    suspend fun getAllPlace(): UiState<BaseResponse<List<PlaceResponse>>>
    suspend fun getTopRating(): UiState<BaseResponse<List<TopRatingPlaceResponse>>>
    suspend fun getRecommendedPlace(): UiState<BaseResponse<RecommendedPlaceResponse>>
    suspend fun getDetailPlaceById(id:String):UiState<BaseResponse<DetailPlace>>
}

class PlaceDataSourceImpl(
    private val apiServices: PlacesServices
) : PlaceDataSource {
    override suspend fun getPlaceBasedOnCity(city: String) = safeApiCall {
        apiServices.getPlaceBasedOnCity(city)
    }

    override suspend fun getAllPlace() = safeApiCall {
        apiServices.getAllPlace()
    }

    override suspend fun getTopRating() = safeApiCall {
        apiServices.getTopRating()
    }

    override suspend fun getRecommendedPlace() = safeApiCall {
        apiServices.getRecommendedPlace()
    }

    override suspend fun getDetailPlaceById(id: String) = safeApiCall {
        apiServices.getDetailPlaceById(id)
    }
}