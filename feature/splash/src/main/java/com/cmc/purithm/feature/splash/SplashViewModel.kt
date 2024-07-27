package com.cmc.purithm.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.exception.AuthException
import com.cmc.purithm.domain.usecase.auth.CheckAccessTokenUseCase
import com.cmc.purithm.domain.usecase.member.CheckFirstRunUseCase
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
class SplashViewModel @Inject constructor(
    private val checkFirstRunUseCase: CheckFirstRunUseCase,
    private val checkAccessTokenUseCase: CheckAccessTokenUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<SplashState> = MutableStateFlow(SplashState.Initialize)
    val state: StateFlow<SplashState> get() = _state.asStateFlow()

    private val _sideEffect : MutableSharedFlow<SplashSideEffect> = MutableSharedFlow()
    val sideEffect : SharedFlow<SplashSideEffect> get() = _sideEffect.asSharedFlow()

    suspend fun checkFirstRun() {
        viewModelScope.launch {
            runCatching {
                checkFirstRunUseCase()
            }.onSuccess {isFirstRun ->
                if(isFirstRun){
                    _state.emit(SplashState.IsFirstRun)
                    _sideEffect.emit(SplashSideEffect.NavigateToOnBoarding)
                } else {
                    checkAccessToken()
                }
            }.onFailure {
                _state.emit(SplashState.Error(""))
            }
        }
    }

    private suspend fun checkAccessToken(){
        viewModelScope.launch {
            runCatching {
                checkAccessTokenUseCase()
            }.onSuccess {
                _state.emit(SplashState.Success)
                _sideEffect.emit(SplashSideEffect.NavigateToHome)
            }.onFailure { exception ->
                when(exception){
                    is AuthException.InvalidTokenException -> {
                        _state.emit(SplashState.Success)
                        _sideEffect.emit(SplashSideEffect.NavigateToLogin)
                    }
                    else -> {
                        _state.emit(SplashState.Error(exception.message.toString()))
                    }
                }
            }
        }
    }
}

sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToOnBoarding : SplashSideEffect
    data object NavigateToHome : SplashSideEffect
}

sealed interface SplashState {
    data object Initialize : SplashState
    data object Loading : SplashState
    data object Success : SplashState
    class Error(val message: String) : SplashState
    data object IsFirstRun : SplashState
}