package com.cmc.purithm.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cmc.purithm.data.remote.ApiConfig
import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.FilterService
import com.cmc.purithm.domain.entity.filter.Filter

internal class ArtistFilterItemDataSource(
    private val filterService: FilterService,
    private val artistId: Long,
    private val sortedBy: String,
    private val totalElementCallback: (Int) -> Unit
) : PagingSource<Int, Filter>() {
    /**
     * 현재 목록을 대체할 새로운 데이터를 로드
     * */
    override fun getRefreshKey(state: PagingState<Int, Filter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * 스크롤 할 때, 데이터를 비동기적으로 가져옴
     * */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Filter> {
        val currentPage = params.key ?: START_PAGE_INDEX

        return try {
            var isLast = false
            val response = HandleApi.callApi {
                filterService.getFilterByPhotographer(
                    sortedBy = this.sortedBy,
                    page = currentPage,
                    photographerId = artistId,
                    size = ApiConfig.PAGE_SIZE
                )
            }
            isLast = response.data?.isLast ?: false
            totalElementCallback(response.data?.totalElement ?: 0)
            val filterList = response.toDomain()
            LoadResult.Page(
                data = filterList,
                prevKey = if (currentPage == START_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (isLast) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "ArtistFilterItemDataSource"
        private const val START_PAGE_INDEX = 0
    }
}