package com.cmc.purithm.data.remote.service

import com.cmc.purithm.data.remote.dto.artist.ArtistResponseDto
import com.cmc.purithm.data.remote.dto.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ArtistService {
    @GET("/api/photographers")
    suspend fun getArtist(
        @Query("sortedBy") sortedBy: String
    ): BaseResponse<List<ArtistResponseDto>>

    @GET("/api/photographers/{photographerId}")
    suspend fun getArtist(
        @Path("photographerId") id : Long
    ) : BaseResponse<ArtistResponseDto>

}