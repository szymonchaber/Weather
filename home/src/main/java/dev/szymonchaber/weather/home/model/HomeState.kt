package dev.szymonchaber.weather.home.model

data class HomeState(
    val forecastState: ForecastLoadingState = ForecastLoadingState.Loading
) {

    companion object {

        val initial: HomeState = HomeState()
    }
}
