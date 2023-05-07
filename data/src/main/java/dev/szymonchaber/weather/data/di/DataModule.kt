package dev.szymonchaber.weather.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.szymonchaber.weather.data.repository.ForecastRepositoryImpl
import dev.szymonchaber.weather.domain.repository.ForecastRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository
}
