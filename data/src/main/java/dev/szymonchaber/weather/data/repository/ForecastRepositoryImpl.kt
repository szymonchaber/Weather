package dev.szymonchaber.weather.data.repository

import dev.szymonchaber.weather.data.api.ForecastApi
import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi
) : ForecastRepository {

    override suspend fun getForecast(location: Location): RequestResult<Weather, WeatherError> {
        return forecastApi.getForecast(location)
    }
}
