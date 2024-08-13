package com.cmc.purithm.domain.usecase.picture

import com.cmc.purithm.domain.repository.PictureRepository
import javax.inject.Inject

class GetPictureUploadUrlUseCase @Inject constructor(
    private val pictureRepository: PictureRepository
){
    suspend operator fun invoke() : String{
        return pictureRepository.getPictureUploadUrl("review")
    }
}