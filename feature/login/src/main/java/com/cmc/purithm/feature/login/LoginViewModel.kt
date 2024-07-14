package com.cmc.purithm.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.auth.LoginForKakaoUseCase
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
class LoginViewModel @Inject constructor(
    private val loginForKakaoUseCase: LoginForKakaoUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initial)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _action: MutableSharedFlow<LoginAction> = MutableSharedFlow()
    val action: SharedFlow<LoginAction> = _action.asSharedFlow()

    private val _sideEffects: MutableSharedFlow<LoginSideEffects> = MutableSharedFlow()
    val sideEffects: SharedFlow<LoginSideEffects> = _sideEffects.asSharedFlow()

    // TODO : 서버 API 나온 후 작업 진행
    fun joinKakao(accessToken: String) {
        viewModelScope.launch {
            _state.emit(LoginState.Loading)
            kotlin.runCatching {
                loginForKakaoUseCase(accessToken)
            }.onSuccess {
                _state.emit(LoginState.Success)
            }.onFailure {
                _state.emit(LoginState.Error(it.message))
            }
        }
    }

    fun startLoginKakao() {
        viewModelScope.launch {
            _action.emit(LoginAction.JoinKakao)
        }
    }
}

/**
 * 사용자가 정하는 것이 아닌 앱에서 나오는 부수효과
 * */
sealed interface LoginSideEffects {
    data object NavigateToJoin : LoginSideEffects
    data object NavigateToMain : LoginSideEffects
}

/**
 * 사용자에 의해 실행되는 액션
 * */
sealed interface LoginAction {
    data object JoinKakao : LoginAction
}

/**
 * 현재 화면의 상태
 * */
sealed interface LoginState {
    data object Initial : LoginState
    data class Error(val message : String?) : LoginState
    data object Loading : LoginState
    data object Success : LoginState
}