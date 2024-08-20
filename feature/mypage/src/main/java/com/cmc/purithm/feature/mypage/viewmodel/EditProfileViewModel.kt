package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.cmc.purithm.domain.usecase.picture.UploadPictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val uploadPictureUseCase: UploadPictureUseCase
) : ViewModel() {

}