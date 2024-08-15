package com.cmc.purithm.feature.artist.model

import com.cmc.purithm.domain.entity.artist.Artist

data class ArtistUiModel(
    val id: Long,
    val name: String,
    val profile: String,
    val description: String,
    val pictures: List<String>
) {
    companion object {
        fun toUiModel(artist: Artist): ArtistUiModel {
            return ArtistUiModel(
                id = artist.id,
                name = artist.name,
                profile = artist.profileImg,
                description = artist.description,
                pictures = artist.pictures
            )
        }
    }
}