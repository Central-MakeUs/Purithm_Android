package com.cmc.purithm.data.remote.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Url

internal interface S3Service {
    @Multipart
    @PUT
    suspend fun uploadPictureToS3(
        @Url presignedUrl: String,
        @Part file : MultipartBody.Part
    ) : Int
}