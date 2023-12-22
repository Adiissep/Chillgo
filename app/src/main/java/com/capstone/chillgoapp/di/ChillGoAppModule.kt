package com.capstone.chillgoapp.di

import com.capstone.chillgoapp.data.AppData
import com.capstone.chillgoapp.data.DefaultDispatcherProvider
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.repository.impl.PlacesRepositoryImpl
import com.capstone.chillgoapp.data.source.PlaceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ChillGoAppModule {
    @Provides
    internal fun providesPlacesSource(): PlaceDataSource =
        AppData.placesDataSource("ecdf800b-2ff4-48e7-9a11-386a7ea37a28.mock.pstmn.io/api") //Gunakan BuildConfig agar tidak terekspose

    @Provides
    internal fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    internal fun providesPlaceRepository(
        dispatcherProvider: DispatcherProvider,
        placesDataSource: PlaceDataSource,
    ): PlacesRepository {
        return PlacesRepositoryImpl(
            dispatcherProvider,
            placeApi = placesDataSource
        )
    }
}