package com.capstone.chillgoapp.data.home

import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.model.RecommendedPlaceModel

data class PlaceUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val data: List<PlaceResponse> = listOf()
)

data class TopPlaceUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val data: List<TopRatingPlaceResponse> = listOf()
)

data class RecommendedPlaceUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val message: String = "",
    val data: List<RecommendedPlaceModel> = listOf()
)