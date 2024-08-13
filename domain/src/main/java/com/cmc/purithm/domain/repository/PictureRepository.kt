package com.cmc.purithm.domain.repository

import java.io.File

interface PictureRepository {
    suspend fun getPictureUploadUrl(prefix : String) : String
    suspend fun uploadPicture(url : String, file : File)
}