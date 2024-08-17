package com.cmc.purithm.domain.usecase.artist

import com.cmc.purithm.domain.repository.ArtistRepository
import javax.inject.Inject

class GetArtistUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
){
    suspend operator fun invoke(artistId : Long) = artistRepository.getArtist(artistId)
}