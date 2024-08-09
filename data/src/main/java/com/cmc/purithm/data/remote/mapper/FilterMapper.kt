package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterListResponseDto
import com.cmc.purithm.domain.entity.filter.Filter

internal fun BaseResponse<FilterListResponseDto>.toDomain(): List<Filter> {
    val listData = this.data?.filters ?: emptyList()
    return listData.map {
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