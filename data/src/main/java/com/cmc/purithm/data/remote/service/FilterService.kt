package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterDescriptionResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterDetailResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterListResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterReviewResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterValueResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FilterService {
    @GET("api/filters")
    suspend fun getFilterList(
        @Query("os") os: String = "AOS",
        @Query("tag") tag: String? = null,
        @Query("sortedBy") sortedBy: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<FilterListResponseDto>

    @POST("api/filters/{filterId}/likes")
    suspend fun requestFilterLike(
        @Path("filterId") filterId: Long
    ): BaseResponse<Unit?>

    @DELETE("api/filters/{filterId}/likes")
    suspend fun deleteFilterLike(
        @Path("filterId") filterId: Long
    ): BaseResponse<Unit?>

    @GET("api/filters/{filterId}")
    suspend fun getFilterDetail(
        @Path("filterId") filterId: Long
    ): BaseResponse<FilterDetailResponseDto>

    @GET("api/filters/{filterId}/AOS")
    suspend fun getFilterValue(
        @Path("filterId") filterId : Long
    ) : BaseResponse<FilterValueResponseDto>

    @GET("api/filters/{filterId}/descriptions")
    suspend fun getFilterDescription(
        @Path("filterId") filterId : Long
    ) : BaseResponse<FilterDescriptionResponseDto>

    @GET("api/photographers/{photographerId}/filters")
    suspend fun getFilterByPhotographer(
        @Path("photographerId") photographerId : Long,
        @Query("sortedBy") sortedBy: String,
        @Query("os") os : String = "AOS",
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : BaseResponse<FilterListResponseDto>
}