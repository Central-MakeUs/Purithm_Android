package com.cmc.purithm.feature.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.entity.review.ReviewItem
import com.cmc.purithm.domain.usecase.member.DeleteMyReviewUseCase
import com.cmc.purithm.domain.usecase.review.GetReviewItemUseCase
import com.cmc.purithm.feature.review.model.ReviewHistoryUiModel
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
class ReviewHistoryViewModel @Inject constructor(
    private val getReviewItemUseCase: GetReviewItemUseCase,
    private val deleteMyReviewUseCase: DeleteMyReviewUseCase
) : ViewModel(){
    private val _state: MutableStateFlow<ReviewHistoryState> = MutableStateFlow(ReviewHistoryState.Initialize)
    val state : StateFlow<ReviewHistoryState> get() = _state.asStateFlow()

    private val _sideEffect : MutableSharedFlow<ReviewHistorySideEffect> = MutableSharedFlow()
    val sideEffect : SharedFlow<ReviewHistorySideEffect> get() = _sideEffect.asSharedFlow()

    fun getReviewItem(reviewId : Long){
        viewModelScope.launch {
            _state.emit(ReviewHistoryState.Loading)
            runCatching {
                getReviewItemUseCase(reviewId)
            }.onSuccess { data ->
                _state.emit(ReviewHistoryState.Success(ReviewHistoryUiModel.toUiModel(data)))
            }.onFailure { exception ->
                _state.emit(ReviewHistoryState.Error(exception.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }
    }

    fun deleteReview(reviewId : Long){
        viewModelScope.launch {
            _state.emit(ReviewHistoryState.Loading)
            runCatching {
                deleteMyReviewUseCase(reviewId)
            }.onSuccess {
                _state.emit(ReviewHistoryState.DeleteSuccess)
            }.onFailure { exception ->
                _state.emit(ReviewHistoryState.Error(exception.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }
    }
}

sealed interface ReviewHistoryState{
    data object Initialize : ReviewHistoryState
    data object Loading : ReviewHistoryState
    class Success(val data : ReviewHistoryUiModel) : ReviewHistoryState
    class Error(val message : String) : ReviewHistoryState
    data object DeleteSuccess : ReviewHistoryState
}

sealed interface ReviewHistorySideEffect{
    data object NavigateFilter : ReviewHistorySideEffect

}
