package dev.szymonchaber.weather.data.api.model

import dev.szymonchaber.weather.domain.model.CurrentWeather
import dev.szymonchaber.weather.domain.model.Temperature
import dev.szymonchaber.weather.domain.model.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal typealias Percentage = Int
internal typealias Celsius = Double

@Serializable
internal data class WeatherDto(
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherDto,
    @SerialName("hourly")
    val hourlyForecast: HourlyForecastDto
) {

    fun toWeather(): Weather {
        return Weather(
            currentWeather = CurrentWeather(Temperature(currentWeather.temperature)),
            hourlyForecast.toHourlyForecasts()
        )
    }
}
