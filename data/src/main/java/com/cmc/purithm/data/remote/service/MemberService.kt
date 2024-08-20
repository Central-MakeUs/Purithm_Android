package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.member.MemberResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface MemberService {
    @POST("/api/users/terms")
    suspend fun requestAgreeTermsOfService(): BaseResponse<Unit>

    @DELETE("/api/users/reviews/{reviewId}")
    suspend fun deleteReview(
        @Path("reviewId") reviewId: Long
    ): BaseResponse<Unit?>

    @GET("/api/users/me")
    suspend fun getUser(): BaseResponse<MemberResponseDto>
}