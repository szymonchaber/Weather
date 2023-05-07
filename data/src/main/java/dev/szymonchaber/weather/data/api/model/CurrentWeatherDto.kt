package dev.szymonchaber.weather.data.api.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CurrentWeatherDto(
    @SerialName("temperature")
    val temperature: Double,
    @SerialName("windspeed")
    val windSpeed: Double,
    @SerialName("winddirection")
    val windDirection: Double,
    @SerialName("weathercode")
    val weatherCode: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("time")
    val time: LocalDateTime,
)
