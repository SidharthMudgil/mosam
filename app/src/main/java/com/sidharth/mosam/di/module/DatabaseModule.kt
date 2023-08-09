package com.sidharth.mosam.di.module

import android.app.Application
import androidx.room.Room
import com.sidharth.mosam.data.local.AppDatabase
import com.sidharth.mosam.data.local.LocalDataSource
import com.sidharth.mosam.data.local.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(
    private val application: Application
) {
    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "mosam_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(weatherDao: WeatherDao): LocalDataSource {
        return LocalDataSource(weatherDao)
    }
}