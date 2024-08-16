package com.cmc.purithm.domain.usecase.filter

import androidx.paging.PagingData
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.repository.FilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFilterByArtistUseCase @Inject constructor(
    private val filterRepository: FilterRepository
){
    suspend operator fun invoke(artistId : Long, sortedBy: String, totalElementCallback: (Int) -> Unit): Flow<PagingData<Filter>> {
        return filterRepository.getFilterOfArtist(
            artistId = artistId,
            sortedBy = sortedBy,
            totalElementCallback = { totalElement ->
                totalElementCallback(totalElement)
            }
        )
    }
}