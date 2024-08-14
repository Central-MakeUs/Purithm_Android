package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.feed.GetFeedResponseDto
import com.cmc.purithm.data.remote.dto.review.AddReviewRequestDto
import com.cmc.purithm.data.remote.dto.review.AddReviewResponseDto
import com.cmc.purithm.data.remote.dto.review.GetFilterReviewResponseDto
import com.cmc.purithm.data.remote.dto.review.ReviewResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ReviewService {
    @GET("/api/filters/{filterId}/reviews")
    suspend fun getFilterReview(
        @Path("filterId") filterId: Long
    ): BaseResponse<GetFilterReviewResponseDto>

    @GET("/api/reviews/{reviewId}")
    suspend fun getReviewItem(
        @Path("reviewId") reviewId: Long
    ): BaseResponse<ReviewResponseDto>

    @POST("/api/reviews")
    suspend fun addReview(
        @Body addReviewRequestDto: AddReviewRequestDto
    ): BaseResponse<AddReviewResponseDto>

    // feed == 전체 리뷰
    @GET("/api/feeds")
    suspend fun getFeeds(
        @Query("os") os: String = "AOS",
        @Query("sortedBy") sortedBy: String
    ): BaseResponse<List<GetFeedResponseDto>>
}