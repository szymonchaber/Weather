package dev.szymonchaber.weather.data.api

import dev.szymonchaber.weather.data.api.model.ForecastDto
import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.model.Temperature
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class ForecastApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getForecast(coordinates: Coordinates): RequestResult<Forecast, ForecastError> {
        return try {
            val body = httpClient
                .get("https://api.open-meteo.com/v1/forecast") {
                    parameter("latitude", coordinates.latitude)
                    parameter("longitude", coordinates.longitude)
                    parameter("current_weather", true)
                }
                .body<ForecastDto>()
            val celsius = body.currentWeather.temperature
            RequestResult.success(Forecast(Temperature(celsius)))
        } catch (exception: Exception) {
            RequestResult.error(ForecastError.UnknownError(exception))
        }
    }
}
