package com.cmc.purithm.domain.usecase.filter

import com.cmc.purithm.domain.repository.FilterRepository
import java.util.logging.Filter
import javax.inject.Inject

class GetFilterValueUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filterId : Long){

    }
}