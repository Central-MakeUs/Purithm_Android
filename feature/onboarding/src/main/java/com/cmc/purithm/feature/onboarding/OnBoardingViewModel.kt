package com.cmc.purithm.feature.onboarding

import android.view.MenuItem.OnActionExpandListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.SetFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setFirstRunUseCase: SetFirstRunUseCase
) : ViewModel() {
    private val _action: MutableSharedFlow<OnBoardingAction> = MutableSharedFlow()
    val action: SharedFlow<OnBoardingAction> get() = _action.asSharedFlow()

    private val _sideEffect: MutableSharedFlow<OnBoardingSideEffect> = MutableSharedFlow()
    val sideEffect: SharedFlow<OnBoardingSideEffect> get() = _sideEffect.asSharedFlow()

    fun clickLoginButton() {
        viewModelScope.launch {
            _action.emit(OnBoardingAction.ClickLoginButton)
        }
    }

    fun checkFirstRun() {
        viewModelScope.launch {
            runCatching {
                setFirstRunUseCase(false)
            }.onSuccess {
                _sideEffect.emit(OnBoardingSideEffect.NavigateToLogin)
            }.onFailure {
                _sideEffect.emit(OnBoardingSideEffect.ShowErrorDialog)
            }
        }
    }
}

sealed interface OnBoardingAction {
    data object ClickLoginButton : OnBoardingAction
}

sealed interface OnBoardingSideEffect {
    // action에서 파생되는 sideEffect가 한개라 action은 구현하지 않음
    data object NavigateToLogin : OnBoardingSideEffect
    data object ShowErrorDialog : OnBoardingSideEffect
}