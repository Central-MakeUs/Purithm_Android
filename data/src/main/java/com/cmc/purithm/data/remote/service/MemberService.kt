package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import retrofit2.http.POST

interface MemberService {
    @POST("/api/users/terms")
    suspend fun requestAgreeTermsOfService() : BaseResponse<Unit>
}