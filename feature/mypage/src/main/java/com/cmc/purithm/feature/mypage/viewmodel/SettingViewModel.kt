package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.auth.SetAccessTokenUseCase
import com.cmc.purithm.domain.usecase.member.SetFirstRunUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val setFirstRunUseCase: SetFirstRunUseCase,
    private val setAccessTokenUseCase: SetAccessTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<SettingState>(SettingState.Initialize)
    val state = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<SettingSideEffects>()
    val sideEffects = _sideEffects.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            try {
                setFirstRunUseCase(true)
                setAccessTokenUseCase("")
                _state.emit(SettingState.SuccessLogout)
            }catch (e : Exception){
                _state.emit(SettingState.Error(e.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }
    }

    fun clickLogout() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.Logout)
        }
    }

    fun clickDeleteAccount() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.DeleteAccount)
        }
    }

    fun clickAccountInfo() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.NavigateToAccountInfo)
        }
    }

    fun clickEditProfile() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.NavigateToEditProfile)
        }
    }

    fun clickTermsOfService() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.NavigateToTermsOfService)
        }
    }

    fun clickPersonalInfo() {
        viewModelScope.launch {
            _sideEffects.emit(SettingSideEffects.NavigateToPersonalInfo)
        }
    }
}

sealed interface SettingState {
    data object Loading : SettingState
    data object SuccessLogout : SettingState
    data class Error(val message: String) : SettingState
    data class SuccessDeleteAccount(val data: String) : SettingState
    data object Initialize : SettingState
}

sealed interface SettingSideEffects {
    data object Logout : SettingSideEffects
    data object DeleteAccount : SettingSideEffects
    data object NavigateToAccountInfo : SettingSideEffects
    data object NavigateToEditProfile : SettingSideEffects
    data object NavigateToTermsOfService : SettingSideEffects
    data object NavigateToPersonalInfo : SettingSideEffects
}