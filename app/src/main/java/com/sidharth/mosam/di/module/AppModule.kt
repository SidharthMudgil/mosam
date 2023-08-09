package com.sidharth.mosam.di.module

import com.sidharth.mosam.data.local.LocalDataSource
import com.sidharth.mosam.data.remote.RemoteDataSource
import com.sidharth.mosam.data.repository.WeatherDataRepositoryImpl
import com.sidharth.mosam.domain.repository.WeatherDataRepository
import com.sidharth.mosam.domain.usecase.GetWeatherDataUseCase
import com.sidharth.mosam.domain.usecase.GetWeatherDataUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {
    @Provides
    @Singleton
    fun provideWeatherDataRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): WeatherDataRepository {
        return WeatherDataRepositoryImpl(
            localDataSource, remoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGetWeatherDataUseCase(
        weatherDataRepository: WeatherDataRepository
    ): GetWeatherDataUseCase {
        return GetWeatherDataUseCaseImpl(weatherDataRepository)
    }
}