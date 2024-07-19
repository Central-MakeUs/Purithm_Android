package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.BaseResponse
import retrofit2.http.GET

import retrofit2.http.POST

internal interface AuthService {
    @GET("auth/kakao")
    suspend fun joinKakao() : BaseResponse<String>

    @GET("api")
    suspend fun checkAccessToken() : BaseResponse<Boolean>
}