package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.GetUserUseCase
import com.cmc.purithm.feature.mypage.model.ProfileUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _state : MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState.Initialize)
    val state : StateFlow<ProfileState> get() = _state.asStateFlow()

    private val _sideEffects : MutableSharedFlow<ProfileSideEffects> = MutableSharedFlow()
    val sideEffects : SharedFlow<ProfileSideEffects> get() = _sideEffects

    fun getUser(){
        viewModelScope.launch {
            _state.emit(ProfileState.Loading)
            runCatching {
                getUserUseCase()
            }.onSuccess { data ->
                _state.emit(ProfileState.Success(ProfileUiModel.toUiModel(data)))
            }.onFailure {
                _state.emit(ProfileState.Error(it.message ?: "알 수 없는 에러가 발생했습니다."))
            }
        }
    }

    fun clickEditProfile(id : Long, username : String, profile : String){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateProfileSetting(id, username, profile))
        }
    }

    fun clickSetting(id : Long, username : String, profile : String){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateSetting(id, username, profile))
        }
    }

    fun clickStamp(){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateStamp)
        }
    }

    fun clickLike(){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateLike)
        }
    }

    fun clickFilterHistory(){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateFilterHistory)
        }
    }

    fun clickReviewHistory(){
        viewModelScope.launch {
            _sideEffects.emit(ProfileSideEffects.NavigateReviewHistory)
        }
    }

    fun refresh(){
        viewModelScope.launch {
            _state.emit(ProfileState.Initialize)
        }
    }

}

sealed interface ProfileState {
    data object Initialize : ProfileState
    data object Loading : ProfileState
    class Success(val data : ProfileUiModel) : ProfileState
    class Error(val message : String) : ProfileState
}

sealed interface ProfileSideEffects {
    data class NavigateSetting(val id : Long, val username : String, val profile : String) : ProfileSideEffects
    data object NavigateStamp : ProfileSideEffects
    data object NavigateLike : ProfileSideEffects
    data object NavigateFilterHistory : ProfileSideEffects
    data object NavigateReviewHistory : ProfileSideEffects
    class NavigateProfileSetting(val id : Long, val username : String, val profile : String) : ProfileSideEffects
}