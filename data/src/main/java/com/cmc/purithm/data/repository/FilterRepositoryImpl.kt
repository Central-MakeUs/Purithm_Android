package com.cmc.purithm.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cmc.purithm.data.remote.ApiConfig
import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.datasource.ArtistFilterItemDataSource
import com.cmc.purithm.data.remote.datasource.FilterItemDataSource
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.FilterService
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FilterRepositoryImpl @Inject constructor(
    private val filterService: FilterService
) : FilterRepository {
    override suspend fun getFilterItems(
        tag: String,
        sortedBy: String,
        page: Int,
        size: Int
    ): Flow<PagingData<Filter>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConfig.PAGE_SIZE
            ),
            pagingSourceFactory = {
                FilterItemDataSource(
                    filterService = filterService,
                    sortedBy = sortedBy,
                    tag = tag
                )
            }
        ).flow
    }

    override suspend fun requestFilterLike(filterId: Long) {
        HandleApi.callApi { filterService.requestFilterLike(filterId) }
    }

    override suspend fun deleteFilterLike(filterId: Long) {
        HandleApi.callApi { filterService.deleteFilterLike(filterId) }
    }

    override suspend fun getFilterDetail(filterId: Long): Filter {
        return HandleApi.callApi { filterService.getFilterDetail(filterId).toDomain() }
    }

    override suspend fun getFilterValue(filterId: Long): Filter {
        return HandleApi.callApi { filterService.getFilterValue(filterId).toDomain() }
    }

    override suspend fun getFilterIntroduction(filterId: Long): Filter {
        return HandleApi.callApi { filterService.getFilterDescription(filterId).toDomain() }
    }

    override suspend fun getFilterOfArtist(
        sortedBy: String,
        artistId: Long
    ): Flow<PagingData<Filter>> {
        return Pager(
            config = PagingConfig(
                pageSize = ApiConfig.PAGE_SIZE
            ),
            pagingSourceFactory = {
                ArtistFilterItemDataSource(
                    filterService = filterService,
                    sortedBy = sortedBy,
                    artistId = artistId,
                )
            }
        ).flow
    }
}