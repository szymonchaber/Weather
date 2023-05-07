package dev.szymonchaber.weather.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import dev.szymonchaber.weather.domain.model.Forecast
import dev.szymonchaber.weather.home.formatter.ImperialValueFormatter
import dev.szymonchaber.weather.home.formatter.MetricValueFormatter
import dev.szymonchaber.weather.home.formatter.ValueFormatter
import dev.szymonchaber.weather.home.model.ForecastLoadingState
import dev.szymonchaber.weather.home.model.HomeState
import dev.szymonchaber.weather.home.model.HomeViewModel

val LocalValueFormatter = compositionLocalOf<ValueFormatter> {
    MetricValueFormatter
}

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState(initial = HomeState.initial)
    Column {
        Text(
            text = "Your GPS seems to be malfunctioning, but we will do our best to forecast the weather for you!",
        )
        CompositionLocalProvider(LocalValueFormatter provides MetricValueFormatter) {
            when (val forecastState = state.forecastState) {
                ForecastLoadingState.Loading -> Text("Loading")
                is ForecastLoadingState.Error -> TODO()
                is ForecastLoadingState.Success -> ForecastView(forecastState.forecast)
            }
        }
    }
}

@Composable
private fun ForecastView(forecast: Forecast) {
    val formatter = LocalValueFormatter.current
    val temperature = remember(forecast) { formatter.format(forecast.temperature) }
    Text("Temperature: $temperature")
}
