package dev.szymonchaber.weather.domain.usecase

import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError
import dev.szymonchaber.weather.domain.model.mapSuccess
import dev.szymonchaber.weather.domain.repository.ForecastRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {

    suspend fun getForecast(location: Location): RequestResult<Weather, WeatherError> {
        return repository.getForecast(location)
            .mapSuccess {
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val futureForecasts = it.hourlyForecasts.filter { forecast ->
                    forecast.time > now
                }
                it.copy(hourlyForecasts = futureForecasts)
            }
    }
}
