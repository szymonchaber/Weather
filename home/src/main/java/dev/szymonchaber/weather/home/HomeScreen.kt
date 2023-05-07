package dev.szymonchaber.weather.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
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
import dev.szymonchaber.weather.domain.model.HourlyForecast
import dev.szymonchaber.weather.home.formatter.MetricValueFormatter
import dev.szymonchaber.weather.home.formatter.ValueFormatter
import dev.szymonchaber.weather.home.model.HomeState
import dev.szymonchaber.weather.home.model.HomeViewModel
import dev.szymonchaber.weather.home.model.WeatherLoadingState
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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
            Header(state)
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
        }
    }
}

@Composable
fun Header(state: HomeState) {
    val formatter = LocalValueFormatter.current
    Column(Modifier.fillMaxWidth()) {
        CurrentTemperature(state, formatter)
        Text(
            modifier = Modifier.Companion.align(Alignment.CenterHorizontally),
            text = state.location?.name ?: "You're lost!",
            style = MaterialTheme.typography.h5
        )
        TeleportationProgress(
            state.teleportationProgress,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )
    }
}

@Composable
private fun ColumnScope.CurrentTemperature(
    state: HomeState,
    formatter: ValueFormatter
) {
    when (val weatherState = state.weatherState) {
        is WeatherLoadingState.Error -> Unit
        WeatherLoadingState.Loading -> CircularProgressIndicator()
        is WeatherLoadingState.Success -> {
            val temperature = remember(state) {
                formatter.format(weatherState.weather.currentWeather.temperature)
            }
            Text(
                modifier = Modifier.Companion.align(Alignment.CenterHorizontally),
                text = temperature,
                style = MaterialTheme.typography.h3
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
                .padding(top = 8.dp),
            progress = progress
        )
    }
}

@Composable
private fun ForecastView(state: WeatherLoadingState.Success) {
    LazyColumn(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(state.weather.hourlyForecasts) {
            HourlyForecastView(it)
        }
    }
}

@Composable
fun HourlyForecastView(hourlyForecast: HourlyForecast) {
    val formatter = LocalValueFormatter.current
    val format = remember {
        DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    }
    with(hourlyForecast) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = format.format(time.toJavaLocalDateTime())
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "🌡️ ${formatter.format(temperature)}"
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "💧 ${precipitationProbability.probability}%"
            )
        }
    }
}
