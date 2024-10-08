package com.cmc.purithm.feature.filter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.filter.DeleteFilterLikeUseCase
import com.cmc.purithm.domain.usecase.filter.GetFilterDetailUseCase
import com.cmc.purithm.domain.usecase.filter.SetFilterLikeUseCase
import com.cmc.purithm.domain.usecase.member.GetFirstFilterRunUseCase
import com.cmc.purithm.domain.usecase.member.SetFirstFilterRunUseCase
import com.cmc.purithm.feature.filter.model.FilterDetailUiModel
import com.cmc.purithm.feature.filter.model.FilterImgType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val setFirstFilterRunUseCase: SetFirstFilterRunUseCase,
    private val getFirstFilterRunUseCase: GetFirstFilterRunUseCase,
    private val getFilterDetailUseCase: GetFilterDetailUseCase,
    private val setFilterLikeUseCase: SetFilterLikeUseCase,
    private val deleteFilterLikeUseCase: DeleteFilterLikeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<FilterState> = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> get() = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<FilterSideEffects> = MutableSharedFlow()
    val sideEffect: SharedFlow<FilterSideEffects> get() = _sideEffect.asSharedFlow()

    init {
        getFilterFirstRun()
    }

    private fun getFilterFirstRun() {
        viewModelScope.launch {
            Log.d(TAG, "getFilterFirstRun: start")
            runCatching {
                getFirstFilterRunUseCase()
            }.onSuccess { flag ->
                _state.update {
                    it.copy(
                        isFirst = flag
                    )
                }
            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        error = exception
                    )
                }
            }
        }
    }

    fun setFilterFirstRun(flag : Boolean) {
        viewModelScope.launch {
            Log.d(TAG, "setFilterFirstRun: start")
            runCatching {
                setFirstFilterRunUseCase(flag)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isFirst = flag
                    )
                }
            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        error = exception
                    )
                }
            }
        }
    }

    fun getFilterDetail(filterId: Long) {
        Log.d(TAG, "getFilterDetail: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getFilterDetailUseCase(filterId)
            }.onSuccess { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        data = FilterDetailUiModel.toUiModel(data)
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

    fun requestFilterLike(filterId: Long) {
        Log.d(TAG, "requestFilterLike: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                setFilterLikeUseCase(filterId)
            }.onSuccess {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
                _sideEffect.emit(FilterSideEffects.ShowFilterLikeSnackBar)
                getFilterDetail(filterId)
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

    fun clickFilterImgType(currentFilterType: FilterImgType) {
        if(state.value.isFirst){
            setFilterFirstRun(false)
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(
                    filterImgType = if (currentFilterType == FilterImgType.FILTER) {
                        FilterImgType.ORIGINAL
                    } else {
                        FilterImgType.FILTER
                    }
                )
            }
        }
    }

    fun clickFilterTextVisibility() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    noText = !it.noText
                )
            }
        }
    }

    fun setCurrentImgIndex(index: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedIndex = index
                )
            }
        }
    }

    fun clickNavigateFilterIntroduction() {
        viewModelScope.launch {
            _sideEffect.emit(FilterSideEffects.NavigateFilterIntroduction)
        }
    }

    fun clickNavigateFilterLoading() {
        viewModelScope.launch {
            _sideEffect.emit(FilterSideEffects.NavigateFilterLoading)
        }
    }

    fun deleteFilterLike(filterId: Long) {
        Log.d(TAG, "deleteFilterLike: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                deleteFilterLikeUseCase(filterId)
            }.onSuccess {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
                getFilterDetail(filterId)
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

    fun clickFilterReview() {
        viewModelScope.launch {
            _sideEffect.emit(FilterSideEffects.NavigateFilterReview)
        }
    }

    companion object {
        private const val TAG = "FilterViewModel"
    }
}

sealed interface FilterSideEffects {
    data object NavigateFilterLoading : FilterSideEffects
    data object NavigateFilterReview : FilterSideEffects
    data object NavigateFilterIntroduction : FilterSideEffects
    data object ShowFilterLikeSnackBar : FilterSideEffects
}

data class FilterState(
    val loading: Boolean = false,
    val isFirst: Boolean = false,
    val error: Throwable? = null,
    val selectedIndex: Int = 1,
    val noText: Boolean = false,
    val filterImgType: FilterImgType = FilterImgType.FILTER,
    val data: FilterDetailUiModel? = null,
)