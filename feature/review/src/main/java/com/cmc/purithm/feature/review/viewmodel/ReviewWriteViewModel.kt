package com.cmc.purithm.feature.review.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.picture.GetPictureUploadUrlUseCase
import com.cmc.purithm.domain.usecase.picture.UploadPictureUseCase
import com.cmc.purithm.domain.usecase.review.AddReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val getPictureUploadUrlUseCase: GetPictureUploadUrlUseCase,
    private val uploadPictureUseCase: UploadPictureUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<ReviewWriteState> = MutableStateFlow(ReviewWriteState())
    val state: StateFlow<ReviewWriteState> get() = _state.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ReviewWriteSideEffect>()
    val sideEffect get() = _sideEffect.asSharedFlow()

    fun addReview() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }
            runCatching {
                addReviewUseCase(
                    filterId = state.value.filterId,
                    content = _state.value.content,
                    pureDegree = _state.value.pureDegree,
                    pictures = _state.value.pictures
                )
            }.onSuccess { reviewId ->
                _state.update {
                    it.copy(loading = false)
                }
                _sideEffect.emit(ReviewWriteSideEffect.ShowReviewSuccessDialog(reviewId))
            }.onFailure { exception ->
                _state.update {
                    it.copy(loading = false, error = exception)
                }
            }
        }
    }

    fun setInitInfo(filterId: Long, thumbnail: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(filterId = filterId, thumbnail = thumbnail)
            }
        }
    }

    fun setContent(content: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(content = content)
            }
        }
    }

    fun setAgree(agree: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(agree = agree)
            }
        }
    }

    fun setIntroduceAgree(agree : Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(introduceAgree = agree)
            }
        }
    }

    fun setPureDegree(pureDegree: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(pureDegree = pureDegree)
            }
        }
    }

    fun requestUploadUrl(file: File) {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }
            runCatching {
                getPictureUploadUrlUseCase("review")
            }.onSuccess { uploadUrl ->
                uploadPicture(uploadUrl, file)
            }.onFailure { exception ->
                _state.update {
                    it.copy(loading = false, error = exception)
                }
            }
        }
    }

    private suspend fun uploadPicture(url: String, file: File) {
        viewModelScope.launch {
            runCatching {
                uploadPictureUseCase(url, file)
            }.onSuccess { code ->
                Log.d(TAG, "setImgUrl: code = $code")
                _state.update {
                    it.copy(loading = false, pictures = it.pictures + url.split("?")[0])
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                _state.update {
                    it.copy(loading = false, error = exception)
                }
            }
        }
    }

    fun startGallery() {
        viewModelScope.launch {
            _sideEffect.emit(ReviewWriteSideEffect.StartGallery)
        }
    }

    fun deleteImgUrl(url: String) {
        Log.d(TAG, "deleteImgUrl: start")
        val list = _state.value.pictures.toMutableList()
        list.remove(url)
        Log.d(TAG, "deleteImgUrl: update = $list")
        _state.update {
            it.copy(pictures = list)
        }
    }

    companion object {
        private const val TAG = "ReviewWriteViewModel"
    }
}

data class ReviewWriteState(
    val filterId: Long = 0,
    val thumbnail: String = "",
    val loading: Boolean = false,
    val error: Throwable? = null,
    val agree: Boolean = false,
    val content: String = "",
    val pureDegree: Int = -1,
    val introduceAgree : Boolean = false,
    val pictures: List<String> = emptyList(),
)

sealed interface ReviewWriteSideEffect {
    data object StartGallery : ReviewWriteSideEffect
    class ShowReviewSuccessDialog(val reviewId: Long) : ReviewWriteSideEffect
}