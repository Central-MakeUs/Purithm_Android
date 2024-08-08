package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterItemResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FilterService {
    @GET("api/filters")
    suspend fun getFilterList(
        @Query("os") os: String = "iOS",
        @Query("tag") tag: String? = null,
        @Query("sortedBy") sortedBy: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): BaseResponse<FilterItemResponseDto>

    @POST("api/filters/{filterId}/likes")
    suspend fun requestFilterLike(
        @Path("filterId") filterId: String
    ) : BaseResponse<Boolean>
}