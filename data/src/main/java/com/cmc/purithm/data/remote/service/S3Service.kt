package com.cmc.purithm.data.remote.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Url

internal interface S3Service {
    @PUT
    suspend fun uploadPictureToS3(
        @Url presignedUrl: String,
        @Body file : RequestBody
    )
}