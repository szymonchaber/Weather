package dev.szymonchaber.weather.domain.model

import kotlinx.datetime.LocalDateTime

data class HourlyForecast(
    val time: LocalDateTime,
    val temperature: Temperature,
    val precipitationProbability: PrecipitationProbability
)
