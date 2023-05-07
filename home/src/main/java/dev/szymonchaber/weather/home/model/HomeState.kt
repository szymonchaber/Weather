package dev.szymonchaber.weather.home.model

import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.domain.model.ForecastError

data class HomeState(
    val forecastState: ForecastLoadingState = ForecastLoadingState.Loading
) {

    fun withForecastSuccess(forecast: Forecast): HomeState {
        return copy(forecastState = ForecastLoadingState.Success(forecast))
    }

    fun withForecastError(forecastError: ForecastError): HomeState {
        return copy(forecastState = ForecastLoadingState.Error(forecastError))
    }

    fun withForecastLoading() : HomeState {
        return copy(forecastState = ForecastLoadingState.Loading)
    }

    companion object {

        val initial: HomeState = HomeState()
    }
}
