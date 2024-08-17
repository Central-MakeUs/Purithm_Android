package com.cmc.purithm.feature.artist.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cmc.purithm.domain.usecase.artist.GetArtistUseCase
import com.cmc.purithm.domain.usecase.artist.GetArtistsUseCase
import com.cmc.purithm.domain.usecase.filter.DeleteFilterLikeUseCase
import com.cmc.purithm.domain.usecase.filter.GetFilterByArtistUseCase
import com.cmc.purithm.domain.usecase.filter.SetFilterLikeUseCase
import com.cmc.purithm.feature.artist.adapter.ArtistFilterAdapter
import com.cmc.purithm.feature.artist.model.ArtistFilterUiModel
import com.cmc.purithm.feature.artist.model.ArtistUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistFilterViewModel @Inject constructor(
    private val getFilterByArtistUseCase: GetFilterByArtistUseCase,
    private val setFilterLikeUseCase: SetFilterLikeUseCase,
    private val deleteFilterLikeUseCase: DeleteFilterLikeUseCase,
    private val getArtistUseCase: GetArtistUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ArtistFilterState> = MutableStateFlow(ArtistFilterState())
    val state: StateFlow<ArtistFilterState> = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<ArtistFilterSideEffects> = MutableSharedFlow()
    val sideEffect: SharedFlow<ArtistFilterSideEffects> = _sideEffect.asSharedFlow()

    fun initArtistId(artistId: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    artistId = artistId
                )
            }
            getFilterByArtist()
        }
    }

    fun getArtist(artistId : Long){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getArtistUseCase(artistId)
            }.onSuccess { artist ->
                _state.update {
                    it.copy(
                        loading = false,
                        artist = ArtistUiModel.toUiModel(artist)
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
                getFilterByArtist()
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

    fun setFilterLike(filterId: Long) {
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
                getFilterByArtist()
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

    private fun getFilterByArtist() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getFilterByArtistUseCase(
                    artistId = state.value.artistId,
                    sortedBy = convertSortedBy(state.value.sortedBy),
                    totalElementCallback = { totalElement ->
                        _state.update {
                            it.copy(
                                filterSize = totalElement
                            )
                        }
                    }
                )
            }.onSuccess { data ->
                data.cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .collect {
                        _state.update { state ->
                            state.copy(
                                loading = false,
                                dataList = it.map { filter ->
                                    ArtistFilterUiModel.toUiModel(filter)
                                }
                            )
                        }
                    }
            }.onFailure { exception ->
                _state.update { state ->
                    state.copy(
                        loading = false,
                        error = exception
                    )
                }
            }
        }
    }

    /**
     * paging adpater의 state를 viewModel에서 관리하도록 설정
     * */
    fun setPageAdapterLoadStateListener(adapter: ArtistFilterAdapter) {
        adapter.addLoadStateListener { loadState ->
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            if (errorState != null) {
                _state.update {
                    it.copy(
                        loading = false,
                        error = errorState.error
                    )
                }
            }
        }
    }

    fun updateArtistSortedBy(sortedBy: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    sortedBy = sortedBy
                )
            }
            getFilterByArtist()
        }
    }

    fun clickFilterSortedBy() {
        viewModelScope.launch {
            _sideEffect.emit(ArtistFilterSideEffects.ShowArtistFilterSortBottomSheet)
        }
    }

    fun clickFilterItem(filterId: Long, canAccess: Boolean) {
        viewModelScope.launch {
            if (canAccess) {
                _sideEffect.emit(ArtistFilterSideEffects.NavigateToFilter(filterId))
            } else {
                _sideEffect.emit(ArtistFilterSideEffects.ShowArtistFilterLockBottomSheet)
            }
        }
    }

    private fun convertSortedBy(sortedBy: String) = when (sortedBy) {
        "조회순" -> "views"
        "최신순" -> "latest"
        "퓨어지수 높은순" -> "pure"
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }
}

sealed interface ArtistFilterSideEffects {
    class NavigateToFilter(val id: Long) : ArtistFilterSideEffects
    data object ShowArtistFilterSortBottomSheet : ArtistFilterSideEffects
    data object ShowArtistFilterLockBottomSheet : ArtistFilterSideEffects
}

data class ArtistFilterState(
    val loading: Boolean = false,
    val artistId: Long = 0L,
    val error: Throwable? = null,
    val artist : ArtistUiModel? = null,
    val dataList: PagingData<ArtistFilterUiModel> = PagingData.empty(),
    val sortedBy: String = "최신순",
    val filterSize: Int = 0
)