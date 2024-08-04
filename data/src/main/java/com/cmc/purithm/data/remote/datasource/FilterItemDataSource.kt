package com.cmc.purithm.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cmc.purithm.data.remote.ApiConfig
import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.FilterService
import com.cmc.purithm.domain.entity.filter.Filter
import java.io.IOException

/**
 * paging data source
 * */
internal class FilterItemDataSource(
    private val filterService: FilterService,
    private val sortedBy: String,
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
            val filterList = HandleApi.callApi {
                val response = filterService.getFilterList(
                    sortedBy = this.sortedBy,
                    page = currentPage,
                    size = ApiConfig.PAGE_SIZE
                )
                isLast = response.data?.isLast ?: false
                response.toDomain()
            }
            LoadResult.Page(
                data = filterList,
                prevKey = if (currentPage == START_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (isLast) null else currentPage + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "FilterItemDataSource"
        private const val START_PAGE_INDEX = 1
    }
}