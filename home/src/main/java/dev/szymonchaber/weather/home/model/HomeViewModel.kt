package dev.szymonchaber.weather.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
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
            timedIndexFlow(10, 1.seconds)
                .flatMapLatest {
                    flow {
                        emit(_state.value.withForecastLoading())
                        emit(
                            when (val result =
                                getForecastUseCase.getForecast(tomorrowCoordinates)) {
                                is RequestResult.Success -> _state.value.withForecastSuccess(result.data)
                                is RequestResult.Error -> _state.value.withForecastError(result.error)
                            }
                        )
                    }
                }
                .collectLatest {
                    _state.tryEmit(it)
                }
        }
    }

    private fun timedIndexFlow(maxIndex: Int, delay: Duration): Flow<Int> {
        return flow {
            var counter = 0
            while (currentCoroutineContext().isActive) {
                counter = (counter + 1) % maxIndex
                emit(counter)
                delay(delay)
            }
        }
    }

    companion object {

        private val tomorrowCoordinates = Coordinates(53.558467, 9.965157)
    }
}
