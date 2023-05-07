package dev.szymonchaber.weather.data.api

import dev.szymonchaber.weather.data.api.model.WeatherDto
import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.Weather
import dev.szymonchaber.weather.domain.model.WeatherError
import dev.szymonchaber.weather.domain.model.RequestResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class ForecastApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun getForecast(location: Location): RequestResult<Weather, WeatherError> {
        return try {
            val body = httpClient
                .get("https://api.open-meteo.com/v1/forecast") {
                    parameter("latitude", location.latitude)
                    parameter("longitude", location.longitude)
                    parameter("current_weather", true)
                    parameter("hourly", "temperature_2m,precipitation_probability")
                }
                .body<WeatherDto>()
            RequestResult.success(body.toWeather())
        } catch (exception: Exception) {
            RequestResult.error(WeatherError.UnknownError(exception))
        }
    }
}
