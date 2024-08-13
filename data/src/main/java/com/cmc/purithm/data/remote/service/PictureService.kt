package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Query

internal interface PictureService {
    @POST("/api/file")
    suspend fun getPictureUploadUrl(
        @Query("prefix") prefix: String
    ): BaseResponse<String>
}