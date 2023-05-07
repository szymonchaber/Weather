package dev.szymonchaber.weather.data.repository

import dev.szymonchaber.weather.data.api.ForecastApi
import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi
) : ForecastRepository {

    override suspend fun getForecast(location: Location): RequestResult<Forecast, ForecastError> {
        return forecastApi.getForecast(location)
    }
}
