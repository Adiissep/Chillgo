package com.capstone.chillgoapp.data

import com.capstone.chillgoapp.data.retrofit.PlacesServices
import com.capstone.chillgoapp.data.source.PlaceDataSource
import com.capstone.chillgoapp.data.source.PlaceDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppData {
    companion object {
        private val REQUEST_TIMEOUT = 5
        private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        fun placesDataSource(
            baseUrl: String
        ): PlaceDataSource {
            val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor { chain ->
                    val origin = chain.request()
                    val url = origin.url.newBuilder() //.addQueryParameter("api-key", "") //Jika punya api key
                        .build()
                    val request =
                        origin.newBuilder().url(url).method(origin.method, origin.body).build()
                    chain.proceed(request)
                }
                .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .readTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .writeTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.MINUTES)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://${baseUrl}/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            val service = retrofit.create(PlacesServices::class.java)

            return PlaceDataSourceImpl(service)
        }

    }
}