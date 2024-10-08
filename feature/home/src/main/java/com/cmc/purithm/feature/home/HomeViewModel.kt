package com.cmc.purithm.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cmc.purithm.domain.usecase.filter.DeleteFilterLikeUseCase
import com.cmc.purithm.domain.usecase.filter.GetFilterItemsUseCase
import com.cmc.purithm.domain.usecase.filter.SetFilterLikeUseCase
import com.cmc.purithm.feature.home.adpater.HomeFilterAdapter
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFilterItemsUseCase: GetFilterItemsUseCase,
    private val setFilterLikeUseCase: SetFilterLikeUseCase,
    private val deleteFilterLikeUseCase: DeleteFilterLikeUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<HomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()

    init {
        viewModelScope.launch {
            getFilters()
        }
    }

    private suspend fun getFilters() {
        Log.d(TAG, "getFilters: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getFilterItemsUseCase(state.value.tag, convertSortedBy(state.value.sortedBy))
            }.onSuccess { filters ->
                filters.cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .onCompletion {
                        Log.d(TAG, "getFilters: onCompletion")
                        _state.update {
                            it.copy(
                                loading = false
                            )
                        }
                    }
                    .onStart {
                        Log.d(TAG, "getFilters: onStart")
                        _state.update {
                            it.copy(
                                loading = true
                            )
                        }
                    }
                    .collectLatest { result ->
                        Log.d(TAG, "getFilters: collectLatest")
                        _state.update { state ->
                            state.copy(
                                loading = false,
                                filterList = result.map { HomeFilterUiModel.toUiModel(it) }
                            )
                        }
                    }
            }.onFailure {
                _state.update { state ->
                    state.copy(
                        error = it
                    )
                }
            }
        }
    }

    fun setFilterLike(filterId: Long) {
        Log.d(TAG, "clickFilterItemLike: start")
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

    fun clickFilterSortedBy() {
        Log.d(TAG, "clickFilterSortedBy: start")
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.ShowFilterSortedBottomSheet)
        }
    }

    /**
     * 필터 정렬 선택
     *
     * @param
     * */
    fun updateFilterSortedBy(sortedBy: String) {
        Log.d(TAG, "updateFilterSortedBy: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    sortedBy = sortedBy,
                    filterList = PagingData.empty()
                )
            }
            getFilters()
        }
    }

    /**
     * 필터 태그 선택
     *
     * @param tag 선택된 params
     * */
    fun clickFilterTag(tag: String) {
        Log.d(TAG, "clickFilterTag: start")
        viewModelScope.launch {
            _state.update {
                it.copy(
                    tag = tag,
                    filterList = PagingData.empty()
                )
            }
            getFilters()
        }
    }

    /**
     * Home Filter Item 클릭
     *
     * @param filterId 클릭된 filter의 id
     * @param canAccess 접근가능 여부
     * */
    fun clickFilterItem(filterId: Long, canAccess: Boolean) {
        Log.d(TAG, "clickFilterItem: start")
        viewModelScope.launch {
            if (canAccess) {
                _sideEffect.emit(HomeSideEffect.NavigateToFilter(filterId))
            } else {
                _sideEffect.emit(HomeSideEffect.ShowFilterLockBottomSheet)
            }
        }
    }

    /**
     * paging adpater의 state를 viewModel에서 관리하도록 설정
     * */
    fun setPageAdapterLoadStateListener(adapter: HomeFilterAdapter) {
        adapter.addLoadStateListener { loadState ->
            val isError =
                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error
            val isLoading =
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading
            val isNothing =
                (loadState.refresh is LoadState.NotLoading) && adapter.itemCount == 0

            Log.d(TAG, "setPageAdapterLoadStateListener: isError = $isError")
            Log.d(TAG, "setPageAdapterLoadStateListener: isNothing = $isNothing")
            Log.d(TAG, "setPageAdapterLoadStateListener: isLoading = $isLoading")
            viewModelScope.launch {
                _state.emit(
                    state.value.copy(
                        isEmpty = isNothing,
                        loading = isLoading
                    )
                )
            }
            if (isError) {
                val errorState = loadState.refresh as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                _state.update {
                    it.copy(
                        loading = false,
                        error = errorState?.error
                    )
                }
            }
        }
    }

    private fun convertSortedBy(sortedBy: String) = when (sortedBy) {
        "이름순" -> "name"
        "멤버십 낮은순" -> "membership"
        "퓨어지수 높은순" -> "pure"
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}

sealed interface HomeSideEffect {
    class NavigateToFilter(val id: Long) : HomeSideEffect
    data object ShowFilterLockBottomSheet : HomeSideEffect
    data object ShowFilterSortedBottomSheet : HomeSideEffect
}


data class HomeState(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val isEmpty: Boolean = false,
    val filterList: PagingData<HomeFilterUiModel> = PagingData.empty(),
    val sortedBy: String = "이름순",
    val tag: String = "",
)