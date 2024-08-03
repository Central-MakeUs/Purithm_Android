package com.cmc.purithm.domain.entity.photographer

data class Photographer(
    val id: Long,
    val thumbnails: List<String>,
    val profile: String,
    val description: String
)
