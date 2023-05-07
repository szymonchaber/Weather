package dev.szymonchaber.weather.home.model

import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError

sealed interface ForecastLoadingState {

    object Loading : ForecastLoadingState

    data class Success(val forecast: Forecast) : ForecastLoadingState

    data class Error(val forecastError: ForecastError) : ForecastLoadingState
}
