package dev.szymonchaber.weather.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.szymonchaber.weather.domain.model.Location
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: Flow<HomeState>
        get() = _state

    init {
        viewModelScope.launch {
            timedIndexFlow(
                maxIndex = coordinateList.lastIndex,
                delay = 10.seconds
            )
                .flatMapConcat {
                    when (it) {
                        is IndexTimerOutput.NextIndex -> {
                            fetchData(it.index)
                        }
                        is IndexTimerOutput.TimerProgressUpdate -> {
                            flowOf(_state.value.copy(teleportationProgress = it.progress))
                        }
                    }
                }
                .onEach {
                    when (val state = it.forecastState) {
                        is ForecastLoadingState.Error -> Timber.e(state.forecastError.toString())
                        else -> Unit
                    }
                }
                .collectLatest {
                    _state.tryEmit(it)
                }
        }
    }

    private fun fetchData(index: Int): Flow<HomeState> {
        return flow {
            emit(_state.value.withForecastLoading())
            val currentLocation = coordinateList[index]
            emit(
                when (val result = getForecastUseCase.getForecast(currentLocation)) {
                    is RequestResult.Success -> {
                        _state.value.withForecastSuccess(result.data, currentLocation)
                    }
                    is RequestResult.Error -> {
                        _state.value.withForecastError(result.error)
                    }
                }
            )
        }
    }

    private fun timedIndexFlow(maxIndex: Int, delay: Duration): Flow<IndexTimerOutput> {
        return flow {
            var index = 0
            var timerMilliseconds = 0
            emit(IndexTimerOutput.NextIndex(index))
            while (currentCoroutineContext().isActive) {
                if (timerMilliseconds >= delay.inWholeMilliseconds) {
                    index = (index + 1) % maxIndex
                    emit(IndexTimerOutput.NextIndex(index))
                    timerMilliseconds = 0
                }
                delay(20)
                timerMilliseconds += 20
                emit(IndexTimerOutput.TimerProgressUpdate(timerMilliseconds / delay.inWholeMilliseconds.toFloat()))
            }
        }
    }

    companion object {

        private val coordinateList = listOf(
            Location(latitude = 53.619653, longitude = 10.079969, name = "Lübeck"),
            Location(latitude = 53.080917, longitude = 8.847533, name = "Bremen"),
            Location(latitude = 52.378385, longitude = 9.794862, name = "Hanover"),
            Location(latitude = 52.496385, longitude = 13.444041, name = "Berlin"),
            Location(latitude = 53.866865, longitude = 10.739542, name = "Kiel"),
            Location(latitude = 54.304540, longitude = 10.152741, name = "Lübeck but different"),
            Location(latitude = 54.797277, longitude = 9.491039, name = "Flensburg"),
            Location(latitude = 52.426412, longitude = 10.821392, name = "Wolfsburg"),
            Location(latitude = 53.542788, longitude = 8.613462, name = "Bremerhaven"),
            Location(latitude = 53.141598, longitude = 8.242565, name = "Oldenburg"),
        )
    }
}

sealed interface IndexTimerOutput {

    data class NextIndex(val index: Int) : IndexTimerOutput
    data class TimerProgressUpdate(val progress: Float) : IndexTimerOutput
}
