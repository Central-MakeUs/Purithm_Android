package com.cmc.purithm.domain.repository

import com.cmc.purithm.domain.entity.artist.Artist

interface ArtistRepository {
    suspend fun getArtists(sortedBy: String): List<Artist>
    suspend fun getArtist(artistId: Long): Artist
}