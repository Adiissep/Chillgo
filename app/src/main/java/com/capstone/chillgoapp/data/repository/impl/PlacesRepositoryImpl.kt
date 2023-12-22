package com.capstone.chillgoapp.data.repository.impl

import android.util.Log
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import com.capstone.chillgoapp.data.source.PlaceDataSource
import com.capstone.chillgoapp.model.RecommendedPlaceModel
import com.capstone.chillgoapp.ui.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlacesRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val placeApi: PlaceDataSource
) : PlacesRepository {
    override suspend fun getPlaceBasedOnCity(city: String): Flow<UiState<List<PlaceResponse>>> =
        flow {
            emit(UiState.Loading)

            when (val result = placeApi.getPlaceBasedOnCity(city)) {
                is UiState.Error -> emit(UiState.Error(result.errorMessage))
                UiState.Loading -> emit(UiState.Loading)
                is UiState.Success -> emit(UiState.Success(result.data.data))
                UiState.Init -> Unit
            }
        }.flowOn(dispatcher.io())

    override suspend fun getAllPlace(): Flow<UiState<List<PlaceResponse>>> = flow {
        emit(UiState.Loading)

        when (val result = placeApi.getAllPlace()) {
            is UiState.Error -> emit(UiState.Error(result.errorMessage))
            UiState.Loading -> emit(UiState.Loading)
            is UiState.Success -> emit(UiState.Success(result.data.data))
            UiState.Init -> Unit
        }
    }.flowOn(dispatcher.io())

    override suspend fun getTopRatingPlace(): Flow<UiState<List<TopRatingPlaceResponse>>> = flow {
        emit(UiState.Loading)

        when (val result = placeApi.getTopRating()) {
            is UiState.Error -> emit(UiState.Error(result.errorMessage))
            UiState.Loading -> emit(UiState.Loading)
            is UiState.Success -> emit(UiState.Success(result.data.data))
            UiState.Init -> Unit
        }
    }.flowOn(dispatcher.io())

    override suspend fun getRecommendedPlace(): Flow<UiState<List<RecommendedPlaceModel>>> = flow {
        emit(UiState.Loading)
        val output = mutableListOf<RecommendedPlaceModel>()

        when (val result = placeApi.getRecommendedPlace()) {
            is UiState.Error -> emit(UiState.Error(result.errorMessage))
            UiState.Loading -> emit(UiState.Loading)
            is UiState.Success -> {
                val recommendedPlaceData =
                    if (result.data.data.recommendedPlaces.size >= 10) result.data.data.recommendedPlaces.take(
                        10
                    ) else result.data.data.recommendedPlaces


                recommendedPlaceData.map { place ->
                    when (val resultPlace = placeApi.getDetailPlaceById("2")) { //TODO: Ubah id sesuai yang ada di iterasi place diatas
                        is UiState.Error -> emit(UiState.Error("Failed get detail: ${resultPlace.errorMessage}"))
                        UiState.Init -> Unit
                        UiState.Loading -> Unit
                        is UiState.Success -> {
                            output.add(resultPlace.data.data.toRecommendedPlaceModel())
                        }
                    }
                }

                emit(UiState.Success(output))
            }

            UiState.Init -> Unit
        }
    }.flowOn(dispatcher.io())
}