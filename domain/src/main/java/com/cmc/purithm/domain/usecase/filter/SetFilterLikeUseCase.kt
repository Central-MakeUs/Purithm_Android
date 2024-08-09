package com.cmc.purithm.domain.usecase.filter

import com.cmc.purithm.domain.repository.FilterRepository
import javax.inject.Inject

class SetFilterLikeUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filterId : Long) {
        filterRepository.requestFilterLike(filterId)
    }
}