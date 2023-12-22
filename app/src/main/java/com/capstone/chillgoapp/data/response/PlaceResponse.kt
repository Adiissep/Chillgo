package com.capstone.chillgoapp.data.response

import com.capstone.chillgoapp.model.RecommendedPlaceModel

data class PlaceResponse(
    val id: Long,
    val place_name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Long,
    val rating: Double,
    val call_number: String,
    val coordinate: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val review: String,
    val schedule_operational: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null
)

data class DetailPlace(
    val id: Long,
    val place_name: String,
    val description: String,
    val category: String,
    val city: String,
    val price: Long,
    val rating: Double,
    val call_number: String,
    val coordinate: String,
    val latitude: Double,
    val longitude: Double,
    val image_url: String,
    val review: String,
    val schedule_operational: String,
    val createdAt: Any? = null,
    val updatedAt: Any? = null,
    val umkMs: List<Any?>
) {
    fun toRecommendedPlaceModel(): RecommendedPlaceModel = RecommendedPlaceModel(
        id = id,
        placePrice = price,
        placeName = place_name,
        placeImageUrl = image_url
    )
}