package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.artist.ArtistResponseDto
import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.domain.entity.artist.Artist
import java.lang.NullPointerException

internal fun BaseResponse<List<ArtistResponseDto>>.toDomain(): List<Artist> {
    val response = data ?: throw NullPointerException("data is null")
    return response.map { it ->
        Artist(
            id = it.id,
            name = it.name,
            description = it.description,
            profileImg = it.profile,
            pictures = it.pictures,
            createdAt = it.createdAt
        )
    }
}