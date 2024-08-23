package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterHistoryResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterLikeResponseDto
import com.cmc.purithm.data.remote.dto.member.GetStampResponseDto
import com.cmc.purithm.data.remote.dto.member.MemberAccountResponseDto
import com.cmc.purithm.data.remote.dto.member.ProfileUpdateRequestDto
import com.cmc.purithm.data.remote.dto.member.MemberResponseDto
import com.cmc.purithm.data.remote.dto.review.ReviewHistoryResponseDto
import retrofit2.http.Body
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

    @GET("/api/users/account")
    suspend fun getAccount() : BaseResponse<MemberAccountResponseDto>

    @POST("/api/users/me")
    suspend fun updateProfile(
        @Body body : ProfileUpdateRequestDto
    ) : BaseResponse<Unit?>

    @GET("/api/users/stamps")
    suspend fun getStamp() : BaseResponse<GetStampResponseDto>

    @GET("/api/users/review")
    suspend fun getReviews() : BaseResponse<List<ReviewHistoryResponseDto>>

    @GET("/api/users/picks")
    suspend fun getLikedFilters() : BaseResponse<List<FilterLikeResponseDto>>

    @GET("/api/users/history")
    suspend fun getFilterHistory() : BaseResponse<FilterHistoryResponseDto>
}
