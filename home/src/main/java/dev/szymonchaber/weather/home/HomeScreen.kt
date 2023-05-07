package dev.szymonchaber.weather.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.szymonchaber.weather.home.formatter.MetricValueFormatter
import dev.szymonchaber.weather.home.formatter.ValueFormatter
import dev.szymonchaber.weather.home.model.WeatherLoadingState
import dev.szymonchaber.weather.home.model.HomeState
import dev.szymonchaber.weather.home.model.HomeViewModel

val LocalValueFormatter = compositionLocalOf<ValueFormatter> {
    MetricValueFormatter
}

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState(initial = HomeState.initial)
    Column(
        Modifier
            .padding(top = 48.dp)
            .padding(horizontal = 24.dp)
    ) {
        CompositionLocalProvider(LocalValueFormatter provides MetricValueFormatter) {
            when (val forecastState = state.weatherState) {
                WeatherLoadingState.Loading -> {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Loading"
                    )
                }

                is WeatherLoadingState.Error -> {
                    Text(text = "An error occured")
                }

                is WeatherLoadingState.Success -> {
                    ForecastView(forecastState)
                }
            }
            TeleportationProgress(
                state.teleportationProgress,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}

@Composable
private fun TeleportationProgress(progress: Float, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Teleportation in progress")
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            progress = progress
        )
    }
}

@Composable
private fun ForecastView(state: WeatherLoadingState.Success) {
    val formatter = LocalValueFormatter.current
    val temperature = remember(state) { formatter.format(state.weather.currentWeather.temperature) }
    LazyColumn(Modifier.fillMaxWidth()) {
        item {
            CurrentWeather(temperature, state)
        }
    }
}

@Composable
private fun CurrentWeather(
    temperature: String,
    state: WeatherLoadingState.Success
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = temperature,
            style = MaterialTheme.typography.h3
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = state.location.name,
            style = MaterialTheme.typography.h5
        )
    }
}
