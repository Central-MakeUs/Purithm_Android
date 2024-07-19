package com.cmc.purithm.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel : ViewModel() {
    private val _sideEffect : MutableSharedFlow<OnBoardingSideEffect> = MutableSharedFlow()
    val sideEffect : SharedFlow<OnBoardingSideEffect> get() = _sideEffect.asSharedFlow()

    fun navigateToLogin() {
        viewModelScope.launch {
            _sideEffect.emit(OnBoardingSideEffect.NavigateToLogin)
        }
    }
}

sealed interface OnBoardingSideEffect {
    // action에서 파생되는 sideEffect가 한개라 action은 구현하지 않음
    data object NavigateToLogin : OnBoardingSideEffect
}