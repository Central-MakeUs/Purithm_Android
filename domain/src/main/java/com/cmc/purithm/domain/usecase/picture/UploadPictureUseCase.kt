package com.cmc.purithm.domain.usecase.picture

import com.cmc.purithm.domain.repository.PictureRepository
import com.cmc.purithm.domain.repository.ReviewRepository
import java.io.File
import javax.inject.Inject

class UploadPictureUseCase @Inject constructor(
    private val pictureRepository: PictureRepository
) {
    suspend operator fun invoke(url: String, file: File): Int {
        return pictureRepository.uploadPicture(url, file)
    }
}