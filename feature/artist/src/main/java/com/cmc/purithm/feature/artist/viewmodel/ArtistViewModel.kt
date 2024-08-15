package com.cmc.purithm.feature.artist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.artist.GetArtistsUseCase
import com.cmc.purithm.feature.artist.model.ArtistUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ArtistState> = MutableStateFlow(ArtistState())
    val state: StateFlow<ArtistState> = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ArtistSideEffects>()
    val sideEffect = _sideEffect.asSharedFlow()

    init {
        getArtists()
    }

    private fun getArtists() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    loading = true
                )
            }
            runCatching {
                getArtistsUseCase(convertSortedBy(state.value.sortedBy))
            }.onSuccess { data ->
                _state.update {
                    it.copy(
                        loading = false,
                        artistSize = data.size,
                        data = data.map { artist ->
                            ArtistUiModel.toUiModel(artist)
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

    fun clickArtist(artistId: Long) {
        viewModelScope.launch {
            _sideEffect.emit(ArtistSideEffects.NavigateArtistFilter(artistId))
        }
    }

    fun updateArtistSortedBy(sortedBy: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    sortedBy = sortedBy
                )
            }
            getArtists()
        }
    }

    fun clickArtistSortedBy() {
        viewModelScope.launch {
            _sideEffect.emit(ArtistSideEffects.ShowArtistFilterBottomSheet)
        }
    }

    private fun convertSortedBy(sortedBy: String) = when (sortedBy) {
        "필터 많은순" -> "filter"
        "최신순" -> "latest"
        "오래된순" -> "earliest"
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }
}

data class ArtistState(
    val loading: Boolean = false,
    // filter, latest, earliest
    val sortedBy: String = "필터 많은순",
    val data: List<ArtistUiModel> = emptyList(),
    val error: Throwable? = null,
    val artistSize: Int = 0
)

sealed interface ArtistSideEffects {
    class NavigateArtistFilter(val artistId: Long) : ArtistSideEffects
    data object ShowArtistFilterBottomSheet : ArtistSideEffects
}