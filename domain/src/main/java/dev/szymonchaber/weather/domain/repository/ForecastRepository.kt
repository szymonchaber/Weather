package dev.szymonchaber.weather.domain.repository

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult

interface ForecastRepository {

    suspend fun getForecast(location: Location): RequestResult<Forecast, ForecastError>
}
