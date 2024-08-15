package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface MemberService {
    @POST("/api/users/terms")
    suspend fun requestAgreeTermsOfService() : BaseResponse<Unit>

    @DELETE("/api/users/reviews/{reviewId}")
    suspend fun deleteReview(
        @Path("reviewId") reviewId: Long
    ) : BaseResponse<Unit?>
}