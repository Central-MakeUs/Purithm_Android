package com.cmc.purithm.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.cmc.purithm.domain.usecase.filter.GetFilterItemsUseCase
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
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
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Initialize)
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
            _state.emit(HomeState.Loading)
            runCatching {
                getFilterItemsUseCase(tag, sortedBy)
            }.onSuccess {
                it.cachedIn(viewModelScope)
                    .distinctUntilChanged()
                    .collect { result ->
                        _state.emit(HomeState.Success(result.map { HomeFilterUiModel.toUiModel(it) }))
                    }
            }.onFailure { exception ->
                _state.emit(HomeState.Error(exception.message!!))
            }
        }
    }

    fun clickFilterSort(){
        viewModelScope.launch {
            _action.emit(HomeAction.ClickFilterSort)
        }
    }
}

sealed interface HomeSideEffect {
    class NavigateToFilter(val id: Long) : HomeSideEffect
}

sealed interface HomeAction {
    data object ClickFilter : HomeAction
    data object ClickFilterSort : HomeAction
}

sealed interface HomeState {
    data object Loading : HomeState
    data object Initialize : HomeState
    class Error(val msg: String) : HomeState
    class Success(val data: PagingData<HomeFilterUiModel>) : HomeState
}