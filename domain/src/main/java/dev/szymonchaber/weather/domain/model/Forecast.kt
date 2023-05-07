package dev.szymonchaber.weather.domain.model

import dev.szymonchaber.weather.domain.model.Temperature

data class Forecast(val temperature: Temperature)

sealed interface ForecastError {

    object NetworkError : ForecastError

    data class UnknownError(val exception: Exception) : ForecastError
}
