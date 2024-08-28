package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.GetFilterHistoryUseCase
import com.cmc.purithm.feature.mypage.model.HistoryUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterHistoryViewModel @Inject constructor(
    private val getFilterHistoryUseCase: GetFilterHistoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<FilterHistoryState>(FilterHistoryState.Initialize)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<FilterHistorySideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun getFilterHistory() {
        viewModelScope.launch {
            _state.emit(FilterHistoryState.Loading)
            runCatching {
                getFilterHistoryUseCase()
            }.onSuccess {
                _state.emit(FilterHistoryState.Success(HistoryUiModel.toUiModel(it)))
            }.onFailure {
                _state.emit(FilterHistoryState.Error(it.message ?: "알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    fun clickFilterThumbnail(filterId: Long, os : String) {
        viewModelScope.launch {
            if(os == "AOS"){
                _sideEffects.emit(FilterHistorySideEffects.NavigateFilter(filterId))
            } else {
                _sideEffects.emit(FilterHistorySideEffects.ShowOsNotMatchSnackBar)
            }
        }
    }

    fun clickFilterValue(filterId : Long, os : String){
        viewModelScope.launch {
            if(os == "AOS"){
                _sideEffects.emit(FilterHistorySideEffects.NavigateFilterValue(filterId))
            } else {
                _sideEffects.emit(FilterHistorySideEffects.ShowOsNotMatchSnackBar)
            }
        }
    }

    fun clickReviewHistory(filterId: Long, reviewId: Long, thumbnail: String, filterName: String) {
        viewModelScope.launch {
            _sideEffects.emit(
                FilterHistorySideEffects.NavigateReviewHistory(
                    filterId,
                    reviewId,
                    thumbnail,
                    filterName
                )
            )
        }
    }

    fun clickFilterWriteReview(filterId: Long, thumbnail: String, filterName: String){
        viewModelScope.launch {
            _sideEffects.emit(
                FilterHistorySideEffects.NavigateReviewWrite(
                    filterId,
                    thumbnail,
                    filterName
                )
            )
        }
    }

    fun clear(){
        viewModelScope.launch {
            _state.emit(FilterHistoryState.Initialize)
        }
    }
}

sealed interface FilterHistoryState {
    data object Initialize : FilterHistoryState
    data object Loading : FilterHistoryState
    data class Success(val data: HistoryUiModel) : FilterHistoryState
    data class Error(val message: String) : FilterHistoryState
}

sealed interface FilterHistorySideEffects {
    data class NavigateFilterValue(val filterId: Long) : FilterHistorySideEffects
    data class NavigateFilter(val filterId: Long) : FilterHistorySideEffects
    data class NavigateReviewHistory(
        val filterId: Long,
        val reviewId: Long,
        val thumbnail: String,
        val filterName: String
    ) : FilterHistorySideEffects

    data class NavigateReviewWrite(
        val filterId: Long,
        val thumbnail: String,
        val filterName: String
    ) : FilterHistorySideEffects
    data object ShowOsNotMatchSnackBar : FilterHistorySideEffects
}