package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.UpdateUserProfileUseCase
import com.cmc.purithm.domain.usecase.picture.GetPictureUploadUrlUseCase
import com.cmc.purithm.domain.usecase.picture.UploadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val uploadPictureUseCase: UploadPictureUseCase,
    private val getPictureUploadUrlUseCase: GetPictureUploadUrlUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditProfileState())
    val state get() = _state.asStateFlow()

    private val _sideEffects = MutableSharedFlow<EditProfileSideEffects>()
    val sideEffects get() = _sideEffects.asSharedFlow()

    fun initInfo(userName : String, profileUrl : String) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    username = userName,
                    profileUrl = profileUrl
                )
            }
        }
    }

    fun setContent(content : String){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    username = content
                )
            }
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }
            runCatching {
                updateUserProfileUseCase(
                    username = _state.value.username,
                    profile = _state.value.profileUrl
                )
            }.onSuccess {
                _state.update {
                    it.copy(loading = false)
                }
                _sideEffects.emit(EditProfileSideEffects.Success)
            }.onFailure { exception ->
                _state.update {
                    it.copy(loading = false, throwable = exception)
                }
            }
        }
    }

    fun requestUploadUrl(file: File) {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }
            runCatching {
                getPictureUploadUrlUseCase("user")
            }.onSuccess { uploadUrl ->
                uploadPicture(uploadUrl, file)
            }.onFailure { exception ->
                _state.update {
                    it.copy(loading = false, throwable = exception)
                }
            }
        }
    }

    private suspend fun uploadPicture(url: String, file: File) {
        viewModelScope.launch {
            runCatching {
                uploadPictureUseCase(url, file)
            }.onSuccess { code ->
                _state.update {
                    it.copy(loading = false, profileUrl = url.split("?")[0])
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                _state.update {
                    it.copy(loading = false, throwable = exception)
                }
            }
        }
    }

    fun clickEditProfile() {
        viewModelScope.launch {
            if(_state.value.profileUrl.isEmpty()){
                _sideEffects.emit(EditProfileSideEffects.StartGallery)
            } else {
                _sideEffects.emit(EditProfileSideEffects.ShowSetProfileImgDialog)
            }
        }
    }

    fun startGallery() {
        viewModelScope.launch {
            _sideEffects.emit(EditProfileSideEffects.StartGallery)
        }
    }

    fun clearProfile(){
        viewModelScope.launch {
            _state.update {
                it.copy(profileUrl = "")
            }
        }
    }

}

data class EditProfileState(
    val loading : Boolean = false,
    val profileUrl : String = "",
    val username : String = "",
    val throwable: Throwable? = null
)

sealed interface EditProfileSideEffects {
    data object StartGallery : EditProfileSideEffects
    data object ShowSetProfileImgDialog : EditProfileSideEffects
    data object Success : EditProfileSideEffects
}