package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterDescriptionResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterDetailResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterListResponseDto
import com.cmc.purithm.data.remote.dto.filter.FilterValueResponseDto
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterImg
import java.lang.NullPointerException

internal fun BaseResponse<FilterListResponseDto>.toDomain(): List<Filter> {
    val response = this.data ?: throw NullPointerException("filter list is null")
    return response.filters.map {
        Filter(
            id = it.id,
            memberShip = it.membership,
            name = it.name,
            thumbnail = it.thumbnail,
            photographerId = it.photographerId,
            photographerName = it.photographerName,
            likes = it.likes,
            canAccess = it.canAccess,
            liked = it.liked
        )
    }
}

@JvmName("filterDetailResponse")
internal fun BaseResponse<FilterDetailResponseDto>.toDomain(): Filter {
    val response = this.data ?: throw NullPointerException("filter value is null")
    return Filter(
        name = response.name,
        likes = response.likes,
        pureDegree = response.pureDegree,
        pictures = response.pictures.map {
            FilterImg(
                picture = it.picture,
                originalPicture = it.originalPicture
            )
        },
        liked = response.liked
    )
}

@JvmName("filterValueResponse")
internal fun BaseResponse<FilterValueResponseDto>.toDomain() : Filter {
    val response = this.data ?: throw NullPointerException("filter value is null")
    return Filter(
        id = response.id,
        name = response.name,
        thumbnail = response.thumbnail,
        liked = response.liked,
        filterValue = response.filterValue
    )
}

@JvmName("filterDescriptionResponse")
internal fun BaseResponse<FilterDescriptionResponseDto>.toDomain() : Filter {
    val response = this.data ?: throw NullPointerException("filter value is null")
    return Filter(
        photographerId = response.photographerId,
        photographerProfile = response.profile ?: "",
        photographerName = response.name,
        description = response.description,
        filterIntroduction = response.photoDescriptions,
        tag = response.tag
    )
}