package com.cmc.purithm.feature.feed.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.review.GetAllReviewUseCase
import com.cmc.purithm.feature.feed.model.FeedUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getAllReviewUseCase: GetAllReviewUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FeedState())
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<FeedSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    init {
        getAllReview()
    }

    private fun getAllReview() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getAllReviewUseCase(convertSortedBy(state.value.sortedBy))
            }.onSuccess { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        totalSize = data.size,
                        data = data.map { reviewItem ->
                            FeedUiModel.toUiModel(reviewItem)
                        }
                    )
                }
            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        loading = false,
                        error = exception
                    )
                }
            }
        }
    }

    fun clickFeedFilter(filterId: Long, canAccess : Boolean) {
        viewModelScope.launch {
            if(canAccess){
                _sideEffects.emit(FeedSideEffects.NavigateFilter(filterId))
            } else {
                _sideEffects.emit(FeedSideEffects.ShowDeniedAccessSnackBar)
            }
        }
    }

    fun clickFeedSort() {
        viewModelScope.launch {
            _sideEffects.emit(FeedSideEffects.ShowFeedSortBottomDialog)
        }
    }

    fun updateFeedSortedBy(sortedBy: String) {
        _state.update {
            it.copy(
                sortedBy = sortedBy
            )
        }
        getAllReview()
    }

    private fun convertSortedBy(sortedBy: String): String {
        return when (sortedBy) {
            "최신순" -> "latest"
            "오래된순" -> "earliest"
            "퓨어지수 높은순" -> "pure"
            else -> throw IllegalArgumentException("Invalid sortedBy value")
        }
    }
}

data class FeedState(
    val loading: Boolean = false,
    val data: List<FeedUiModel> = emptyList(),
    val error: Throwable? = null,
    val totalSize: Int = 0,
    val sortedBy: String = "최신순"
)

sealed interface FeedSideEffects {
    data object ShowFeedSortBottomDialog : FeedSideEffects
    class NavigateFilter(val filterId: Long) : FeedSideEffects
    data object ShowDeniedAccessSnackBar : FeedSideEffects
}