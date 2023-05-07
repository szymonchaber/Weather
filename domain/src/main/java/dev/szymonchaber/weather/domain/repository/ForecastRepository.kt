package dev.szymonchaber.weather.domain.repository

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError
import dev.szymonchaber.weather.domain.model.RequestResult

interface ForecastRepository {

    suspend fun getForecast(location: Location): RequestResult<Weather, WeatherError>
}
