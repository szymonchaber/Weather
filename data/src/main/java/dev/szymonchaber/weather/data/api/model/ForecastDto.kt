package dev.szymonchaber.weather.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ForecastDto(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDto
)
