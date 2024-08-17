package com.cmc.purithm.feature.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.filter.DeleteFilterLikeUseCase
import com.cmc.purithm.domain.usecase.filter.GetFilterValueUseCase
import com.cmc.purithm.domain.usecase.filter.SetFilterLikeUseCase
import com.cmc.purithm.feature.filter.model.FilterValueUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterValueViewModel @Inject constructor(
    private val getFilterValueUseCase: GetFilterValueUseCase,
    private val setFilterLikeUseCase: SetFilterLikeUseCase,
    private val deleteFilterLikeUseCase: DeleteFilterLikeUseCase
) : ViewModel() {
    private val _state : MutableStateFlow<FilterValueState> = MutableStateFlow(FilterValueState.Initialize)
    val state : StateFlow<FilterValueState> get() = _state.asStateFlow()

    private val _sideEffect : MutableSharedFlow<FilterValueSideEffects> = MutableSharedFlow()
    val sideEffect : SharedFlow<FilterValueSideEffects> get() = _sideEffect.asSharedFlow()


    fun getFilterValue(filterId : Long) {
        viewModelScope.launch {
            _state.emit(FilterValueState.Loading)
            runCatching {
                getFilterValueUseCase(filterId)
            }.onSuccess { data ->
                _state.emit(FilterValueState.Success(FilterValueUiModel.toUiModel(data)))
            }.onFailure { exception ->
                _state.emit(FilterValueState.Error(exception.message ?: "알 수 없는 오류 발생"))
            }
        }
    }

    fun requestFilterLike(filterId : Long, liked : Boolean) {
        viewModelScope.launch {
            _state.emit(FilterValueState.Loading)
            runCatching {
                if(liked){
                    deleteFilterLikeUseCase(filterId)
                } else {
                    setFilterLikeUseCase(filterId)
                }
            }.onSuccess {
                _state.emit(FilterValueState.LikeResult(!liked))
                if(!liked){
                    _sideEffect.emit(FilterValueSideEffects.ShowFilterLikeSnackBar)
                }
            }.onFailure { exception ->
                _state.emit(FilterValueState.Error(exception.message ?: "알 수 없는 오류 발생"))
            }
        }
    }

    fun clickFilterGuide(){
        viewModelScope.launch {
            _sideEffect.emit(FilterValueSideEffects.ShowFilterGuideDialog)
        }
    }
}

sealed interface FilterValueState {
    data object Initialize : FilterValueState
    data object Loading : FilterValueState
    class LikeResult(val result : Boolean) : FilterValueState
    class Error(val message : String) : FilterValueState
    class Success(val data : FilterValueUiModel) : FilterValueState
}

sealed interface FilterValueSideEffects {
    data object ShowFilterLikeSnackBar : FilterValueSideEffects
    data object ShowFilterGuideDialog : FilterValueSideEffects
}
