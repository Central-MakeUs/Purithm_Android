package com.cmc.purithm.feature.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.entity.review.Review
import com.cmc.purithm.domain.usecase.review.GetFilterReviewUseCase
import com.cmc.purithm.feature.filter.model.FilterReviewListUiModel
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
class FilterReviewViewModel @Inject constructor(
    private val getFilterReviewUseCase: GetFilterReviewUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<FilterReviewState> = MutableStateFlow(FilterReviewState())
    val state: StateFlow<FilterReviewState> get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<FilterReviewSideEffects> = MutableSharedFlow()
    val sideEffect: SharedFlow<FilterReviewSideEffects> get() = _sideEffect.asSharedFlow()

    fun getFilterReview(filterId: Long) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    loading = true
                )
            )
            runCatching {
                getFilterReviewUseCase(filterId)
            }.onSuccess { data ->
                _state.emit(
                    _state.value.copy(
                        loading = false,
                        data = FilterReviewListUiModel.toUiModel(data)
                    )
                )
            }.onFailure {
                _state.emit(
                    _state.value.copy(
                        loading = false
                    )
                )
            }
        }
    }

    fun clickFilterReviewGuide(){
        viewModelScope.launch {
            _sideEffect.emit(FilterReviewSideEffects.ShowFilterReviewGuideDialog)
        }
    }
}

data class FilterReviewState(
    val loading: Boolean = false,
    val data: FilterReviewListUiModel? = null,
)

sealed interface FilterReviewSideEffects {
    class NavigateFilterReviewDetail(reviewId: Long) : FilterReviewSideEffects
    data object ShowFilterReviewGuideDialog : FilterReviewSideEffects
}