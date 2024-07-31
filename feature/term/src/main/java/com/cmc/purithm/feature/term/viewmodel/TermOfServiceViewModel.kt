package com.cmc.purithm.feature.term.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.AgreeToTermsOfServiceUseCase
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
class TermOfServiceViewModel @Inject constructor(
    private val agreeToTermsOfServiceUseCase: AgreeToTermsOfServiceUseCase
) : ViewModel(){
    private val _state: MutableStateFlow<TermsOfServiceState> = MutableStateFlow(TermsOfServiceState.Initial)
    val state: StateFlow<TermsOfServiceState> = _state.asStateFlow()

    private val _sideEffect : MutableSharedFlow<TermsOfServiceSideEffects> = MutableSharedFlow()
    val sideEffect : SharedFlow<TermsOfServiceSideEffects> = _sideEffect.asSharedFlow()

    private val _action : MutableSharedFlow<TermsOfServiceAction> = MutableSharedFlow()
    val action : SharedFlow<TermsOfServiceAction> = _action.asSharedFlow()

    fun requestAgreeToTermsOfService() {
        viewModelScope.launch {
            _state.emit(TermsOfServiceState.Loading)
            runCatching {
                agreeToTermsOfServiceUseCase()
            }.onSuccess {
                _state.emit(TermsOfServiceState.Success)
                _sideEffect.emit(TermsOfServiceSideEffects.NavigateJoinComplete)
            }.onFailure {
                _state.emit(TermsOfServiceState.Error(it.message ?: "알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    fun clickAgreeToTermsOfService() {
        viewModelScope.launch {
            _action.emit(TermsOfServiceAction.RequestAgreeToTermsOfService)
        }
    }
}

sealed interface TermsOfServiceSideEffects {
    data object NavigateJoinComplete : TermsOfServiceSideEffects
}

sealed interface TermsOfServiceAction {
    data object RequestAgreeToTermsOfService : TermsOfServiceAction
}

sealed interface TermsOfServiceState {
    data object Initial : TermsOfServiceState
    data object Loading : TermsOfServiceState
    data object Success : TermsOfServiceState
    class Error(val message: String) : TermsOfServiceState
}