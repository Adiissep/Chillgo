package com.capstone.chillgoapp.data.response

data class BaseResponse<T> (
    val error: Boolean,
    val message: String,
    val data: T
)