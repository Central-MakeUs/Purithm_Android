package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.BaseResponse
import com.cmc.purithm.data.remote.dto.auth.ResponseAuthKakao
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {
    @POST("auth/code/kakao")
    suspend fun joinKakao(
        @Body data : String
    ) : BaseResponse<ResponseAuthKakao>
}