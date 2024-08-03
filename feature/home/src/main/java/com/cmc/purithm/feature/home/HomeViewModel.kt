package com.cmc.purithm.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.usecase.filter.GetFilterItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFilterItemsUseCase: GetFilterItemsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _action: MutableSharedFlow<HomeAction> = MutableSharedFlow()
    val action: SharedFlow<HomeAction> = _action.asSharedFlow()

    private val _sideEffect: MutableSharedFlow<HomeSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffect.asSharedFlow()

    fun getFilters(
        tag: String = "",
        sortedBy: String = "latest"
    ) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getFilterItemsUseCase(tag, sortedBy)
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collect { result ->
                    _state.update {
                        it.copy(
                            loading = false,
                            filters = result
                        )
                    }
                }
        }
    }
}

sealed interface HomeSideEffect {
    class NavigateToFilter(id: Long) : HomeSideEffect
}

sealed interface HomeAction {
    data object ClickFilter : HomeAction
    data object ClickTag : HomeAction
}

data class HomeState(
    var loading: Boolean = false,
    var error: Throwable? = null,
    var filters: PagingData<Filter>? = null,
    var sortedBy: String = "",
    var tag: String = ""
)