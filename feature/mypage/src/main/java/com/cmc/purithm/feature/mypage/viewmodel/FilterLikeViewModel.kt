package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.filter.DeleteFilterLikeUseCase
import com.cmc.purithm.domain.usecase.filter.SetFilterLikeUseCase
import com.cmc.purithm.domain.usecase.member.GetLikedFiltersUseCase
import com.cmc.purithm.feature.mypage.model.FilterLikeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterLikeViewModel @Inject constructor(
    private val getFilterLikeUseCase: GetLikedFiltersUseCase,
    private val setFilterLikeUseCase: SetFilterLikeUseCase,
    private val deleteFilterLikeUseCase: DeleteFilterLikeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<FilterLikeState>(FilterLikeState.Initialized)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<FilterLikeSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun getFilterLike() {
        viewModelScope.launch {
            _state.emit(FilterLikeState.Loading)
            runCatching {
                getFilterLikeUseCase()
            }.onSuccess { list ->
                _state.emit(FilterLikeState.Success(list.map {
                    FilterLikeUiModel.toUiModel(it)
                }))
            }.onFailure {
                _state.emit(FilterLikeState.Error(it.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }
    }

    fun setLikeFilter(filterId : Long) {
        viewModelScope.launch {
            _state.emit(FilterLikeState.Loading)
            runCatching {
                setFilterLikeUseCase(filterId)
            }.onSuccess {
                _state.emit(FilterLikeState.SetLikeSuccess)
            }.onFailure {
                _state.emit(FilterLikeState.FailFilterLike)
            }
        }
    }

    fun deleteLikeFilter(filterId : Long){
        viewModelScope.launch {
            _state.emit(FilterLikeState.Loading)
            runCatching {
                deleteFilterLikeUseCase(filterId)
            }.onSuccess {
                _state.emit(FilterLikeState.DeleteLikeSuccess)
            }.onFailure {
                _state.emit(FilterLikeState.FailFilterLike)
            }
        }
    }

    fun clickFilterItem(filterId : Long){
        viewModelScope.launch {
            _sideEffects.emit(FilterLikeSideEffects.NavigateFilter(filterId))
        }
    }
}

sealed interface FilterLikeState {
    data object Loading : FilterLikeState
    data object Initialized : FilterLikeState
    data class Success(val data: List<FilterLikeUiModel>) : FilterLikeState
    data object DeleteLikeSuccess : FilterLikeState
    data object SetLikeSuccess : FilterLikeState
    data class Error(val message: String) : FilterLikeState
    data object FailFilterLike : FilterLikeState
}

sealed interface FilterLikeSideEffects {
    data class NavigateFilter(val id: Long) : FilterLikeSideEffects
}