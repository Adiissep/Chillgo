package com.capstone.chillgoapp.data.repository

import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.RecommendedPlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.model.RecommendedPlaceModel
import com.capstone.chillgoapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    suspend fun getPlaceBasedOnCity(
        city: String
    ): Flow<UiState<List<PlaceResponse>>>

    suspend fun getAllPlace(): Flow<UiState<List<PlaceResponse>>>

    suspend fun getTopRatingPlace(): Flow<UiState<List<TopRatingPlaceResponse>>>

    suspend fun getRecommendedPlace() : Flow<UiState<List<RecommendedPlaceModel>>>
}