package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.GetStampUseCase
import com.cmc.purithm.feature.mypage.model.StampUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StampHistoryViewModel @Inject constructor(
    private val getStampUseCase: GetStampUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<StampHistoryState>(StampHistoryState.Initialize)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<StampSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        getStamps()
    }

    private fun getStamps() {
        viewModelScope.launch {
            _state.emit(StampHistoryState.Loading)
            runCatching {
                getStampUseCase()
            }.onSuccess {
                _state.emit(StampHistoryState.Success(StampUiModel.toUiModel(it)))
            }.onFailure { exception ->
                _state.emit(StampHistoryState.Error(exception.message ?: "알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    fun clickStampLockDialog() {
        viewModelScope.launch {
            _sideEffects.emit(StampSideEffects.ShowFilterLockGuide)
        }
    }

    fun clickReviewHistory(filterId: Long, reviewId: Long, thumbnail: String, filterName: String) {
        viewModelScope.launch {
            _sideEffects.emit(StampSideEffects.NavigateReviewHistory(filterId, reviewId, thumbnail, filterName))
        }
    }
}


sealed interface StampHistoryState {
    data object Initialize : StampHistoryState
    data object Loading : StampHistoryState
    data class Success(val data: StampUiModel) : StampHistoryState
    data class Error(val message: String) : StampHistoryState
}

sealed interface StampSideEffects {
    data object ShowFilterLockGuide : StampSideEffects
    data object NavigateFilterHistory : StampSideEffects
    data class NavigateReviewHistory(
        val filterId: Long,
        val reviewId: Long,
        val thumbnail: String,
        val filterName: String
    ) : StampSideEffects
}