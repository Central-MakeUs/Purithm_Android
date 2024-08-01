package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.auth.JoinKakaoResponseDto
import com.cmc.purithm.data.remote.dto.base.BaseResponse
import retrofit2.http.GET

internal interface AuthService {
    @GET("auth/kakao")
    suspend fun joinKakao() : BaseResponse<JoinKakaoResponseDto>

    @GET("api")
    suspend fun checkAccessToken() : BaseResponse<Unit?>
}