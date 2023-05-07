package dev.szymonchaber.weather.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.szymonchaber.weather.domain.model.Coordinates
import dev.szymonchaber.weather.domain.model.RequestResult
import dev.szymonchaber.weather.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: Flow<HomeState>
        get() = _state

    init {
        viewModelScope.launch {
            when (val result = getForecastUseCase.getForecast(tomorrowCoordinates)) {
                is RequestResult.Success -> {
                    _state.value =
                        _state.value.copy(forecastState = ForecastLoadingState.Success(result.data))
                }

                is RequestResult.Error -> {
                    _state.value =
                        _state.value.copy(forecastState = ForecastLoadingState.Error(result.error))
                }
            }
        }
    }

    companion object {

        private val tomorrowCoordinates = Coordinates(53.558467, 9.965157)
    }
}
