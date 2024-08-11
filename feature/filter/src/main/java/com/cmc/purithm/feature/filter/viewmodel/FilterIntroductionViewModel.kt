package com.cmc.purithm.feature.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.filter.GetFilterIntroductionUseCase
import com.cmc.purithm.feature.filter.model.FilterIntroductionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterIntroductionViewModel @Inject constructor(
    private val getFilterIntroductionUseCase: GetFilterIntroductionUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<FilterIntroductionState> =
        MutableStateFlow(FilterIntroductionState.Initialize)
    val state: StateFlow<FilterIntroductionState> get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<FilterIntroductionSideEffect> = MutableSharedFlow()
    val sideEffect get() = _sideEffect.asSharedFlow()

    fun getFilterIntroduction(filterId: Long) {
        viewModelScope.launch {
            _state.emit(FilterIntroductionState.Loading)
            runCatching {
                getFilterIntroductionUseCase(filterId)
            }.onSuccess { data ->
                _state.emit(FilterIntroductionState.Success(FilterIntroductionUiModel.toUiModel(data)))
            }.onFailure { exception ->
                _state.emit(FilterIntroductionState.Error(exception.message ?: "알 수 없는 오류 발생"))
            }
        }
    }

    fun clickPhotographerShop(photographerId : Long){
        viewModelScope.launch {
            _sideEffect.emit(FilterIntroductionSideEffect.NavigateArtistShop(photographerId))
        }
    }
}

sealed interface FilterIntroductionState {
    data object Loading : FilterIntroductionState
    data object Initialize : FilterIntroductionState
    class Success(val data: FilterIntroductionUiModel) : FilterIntroductionState
    class Error(val message: String) : FilterIntroductionState
}

sealed interface FilterIntroductionSideEffect {
    class NavigateArtistShop(val artistId: Long) : FilterIntroductionSideEffect
}
