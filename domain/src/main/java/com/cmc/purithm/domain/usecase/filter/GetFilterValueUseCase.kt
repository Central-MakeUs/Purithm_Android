package com.cmc.purithm.domain.usecase.filter

import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.repository.FilterRepository
import javax.inject.Inject

class GetFilterValueUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filterId : Long) : Filter {
        return filterRepository.getFilterValue(filterId)
    }
}