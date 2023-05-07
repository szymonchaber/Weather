package dev.szymonchaber.weather.data.repository

import dev.szymonchaber.weather.data.api.ForecastApi
import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.model.Temperature
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
internal class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi
) : ForecastRepository {

    override suspend fun getForecast(coordinates: Coordinates): RequestResult<Forecast, ForecastError> {
        return forecastApi.getForecast(coordinates)
    }
}
