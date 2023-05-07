package dev.szymonchaber.weather.data.repository

import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.model.Temperature
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ForecastRepositoryImpl @Inject constructor() : ForecastRepository {

    override suspend fun getForecast(coordinates: Coordinates): RequestResult<Forecast, ForecastError> {
        delay(1000)
        return RequestResult.success(Forecast(Temperature(32.0)))
    }
}
