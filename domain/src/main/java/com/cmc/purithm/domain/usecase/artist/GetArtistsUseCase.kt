package com.cmc.purithm.domain.usecase.artist

import com.cmc.purithm.domain.entity.artist.Artist
import com.cmc.purithm.domain.repository.ArtistRepository
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    suspend operator fun invoke(sortedBy : String) : List<Artist> {
        return artistRepository.getArtists(sortedBy)
    }
}