package dev.szymonchaber.weather.data.api.model

import dev.szymonchaber.weather.domain.model.HourlyForecast
import dev.szymonchaber.weather.domain.model.PrecipitationProbability
import dev.szymonchaber.weather.domain.model.Temperature
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class HourlyForecastDto(
    @SerialName("time")
    val time: List<LocalDateTime>,
    @SerialName("temperature_2m")
    val temperature: List<Celsius>,
    @SerialName("precipitation_probability")
    val precipitationProbability: List<Percentage>
) {

    fun toHourlyForecasts(): List<HourlyForecast> {
        check(time.size == temperature.size && time.size == precipitationProbability.size) {
            "The api returned incorrect size of hourly forecast data"
        }
        return time.zip(temperature)
            .zip(precipitationProbability) { (time, temperature), precipitationProbability ->
                HourlyForecast(
                    time,
                    Temperature(temperature),
                    PrecipitationProbability(precipitationProbability)
                )
            }
    }
}
