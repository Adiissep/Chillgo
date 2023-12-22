package com.capstone.chillgoapp.data.retrofit

import com.capstone.chillgoapp.data.response.BaseResponse
import com.capstone.chillgoapp.data.response.DetailPlace
import com.capstone.chillgoapp.data.response.PlaceResponse
import com.capstone.chillgoapp.data.response.RecommendedPlaceResponse
import com.capstone.chillgoapp.data.response.TopRatingPlaceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlacesServices {
    @GET("places-by-region/{city}")
    suspend fun getPlaceBasedOnCity(
        @Path("city") city: String
    ): Response<BaseResponse<List<PlaceResponse>>>

    @GET("places")
    suspend fun getAllPlace(): Response<BaseResponse<List<PlaceResponse>>>

    @GET("toprating")
    suspend fun getTopRating(): Response<BaseResponse<List<TopRatingPlaceResponse>>>

    @GET("recommended-places")
    suspend fun getRecommendedPlace() : Response<BaseResponse<RecommendedPlaceResponse>>

    @GET("places/{id}")
    suspend fun getDetailPlaceById(
        @Path("id") id: String
    ) : Response<BaseResponse<DetailPlace>>
}