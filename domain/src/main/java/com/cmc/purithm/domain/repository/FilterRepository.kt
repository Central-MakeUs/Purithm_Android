package com.cmc.purithm.domain.repository

import androidx.paging.PagingData
import com.cmc.purithm.domain.entity.filter.Filter
import kotlinx.coroutines.flow.Flow

interface FilterRepository {
    suspend fun getFilterItems(
        tag: String,
        sortedBy: String,
        page: Int = 0,
        size: Int = 0
    ): Flow<PagingData<Filter>>

    suspend fun requestFilterLike(
        filterId: Long
    )

    suspend fun deleteFilterLike(
        filterId: Long
    )

    suspend fun getFilterDetail(
        filterId : Long
    ) : Filter

    suspend fun getFilterValue(
        filterId : Long
    ) : Filter

    suspend fun getFilterIntroduction(
        filterId : Long
    ) : Filter

    suspend fun getFilterOfArtist(
        artistId : Long
    ) : Flow<PagingData<Filter>>
}