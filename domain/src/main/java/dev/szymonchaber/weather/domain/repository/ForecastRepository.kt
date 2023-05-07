package dev.szymonchaber.weather.domain.repository

import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult

interface ForecastRepository {

    suspend fun getForecast(coordinates: Coordinates): RequestResult<Forecast, ForecastError>
}
