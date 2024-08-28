package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.DeleteMyReviewUseCase
import com.cmc.purithm.domain.usecase.member.GetReviewHistoryUseCase
import com.cmc.purithm.feature.mypage.model.ReviewHistoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewHistoryViewModel @Inject constructor(
    private val getReviewHistoryUseCase: GetReviewHistoryUseCase,
    private val deleteMyReviewUseCase: DeleteMyReviewUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ReviewHistoryState> =
        MutableStateFlow(ReviewHistoryState.Initialize)
    val state = _state.asStateFlow()

    private val _sideEffects: MutableSharedFlow<ReviewHistorySideEffects> = MutableSharedFlow()
    val sideEffects = _sideEffects.asSharedFlow()

    fun getReviewHistory() {
        viewModelScope.launch {
            _state.emit(ReviewHistoryState.Loading)
            runCatching {
                getReviewHistoryUseCase()
            }.onSuccess { list ->
                _state.emit(ReviewHistoryState.Success(list.map {
                    ReviewHistoryUiModel.toUiModel(it)
                }))
            }.onFailure {
                _state.emit(ReviewHistoryState.Error(it.message ?: "알 수 없는 에러"))
            }
        }
    }

    fun clickFilter(filterId : Long, os : String) {
        viewModelScope.launch {
            if(os == "AOS"){
                _sideEffects.emit(ReviewHistorySideEffects.NavigateFilter(filterId))
            } else {
                _sideEffects.emit(ReviewHistorySideEffects.ShowOsNotMatchSnackBar)
            }
        }
    }

    fun deleteReview(reviewId : Long) {
        viewModelScope.launch {
            _state.emit(ReviewHistoryState.Loading)
            runCatching {
                deleteMyReviewUseCase(reviewId)
            }.onSuccess {
                _sideEffects.emit(ReviewHistorySideEffects.SuccessDeleteReview)
            }.onFailure {
                _state.emit(ReviewHistoryState.Error(it.message ?: "알 수 없는 에러"))
            }
        }
    }

    fun clickDeleteReview(reviewId: Long){
        viewModelScope.launch {
            _sideEffects.emit(ReviewHistorySideEffects.ShowDeleteReviewDialog(reviewId))
        }
    }

    fun init(){
        viewModelScope.launch {
            _state.emit(ReviewHistoryState.Initialize)
        }
    }
}

sealed interface ReviewHistoryState {
    data object Initialize : ReviewHistoryState
    data object Loading : ReviewHistoryState
    data class Success(val data: List<ReviewHistoryUiModel>) : ReviewHistoryState
    data class Error(val message: String) : ReviewHistoryState
}

sealed interface ReviewHistorySideEffects {
    data class NavigateFilter(val filterId : Long) : ReviewHistorySideEffects
    data object SuccessDeleteReview : ReviewHistorySideEffects
    data class ShowDeleteReviewDialog(val reviewId : Long) : ReviewHistorySideEffects
    data object ShowOsNotMatchSnackBar : ReviewHistorySideEffects
}