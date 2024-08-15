package com.cmc.purithm.data.repository

import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.toDomain
import com.cmc.purithm.data.remote.service.ArtistService
import com.cmc.purithm.domain.entity.artist.Artist
import com.cmc.purithm.domain.repository.ArtistRepository
import javax.inject.Inject

internal class ArtistRepositoryImpl @Inject constructor(
    private val artistService: ArtistService
) : ArtistRepository {
    override suspend fun getArtists(sortedBy : String): List<Artist> {
        return HandleApi.callApi { artistService.getArtist(sortedBy).toDomain() }
    }
}