package com.cmc.purithm.domain.usecase.filter

import androidx.paging.PagingData
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilterItemsUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(tag: String = "", sortedBy: String = ""): Flow<PagingData<Filter>> {
        return filterRepository.getFilterItems(
            tag = tag, sortedBy = sortedBy
        )
    }
}