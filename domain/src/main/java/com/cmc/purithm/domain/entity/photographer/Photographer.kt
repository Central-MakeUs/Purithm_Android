package com.cmc.purithm.domain.entity.photographer

data class Photographer(
    val id: Long = 0L,
    val thumbnails: List<String> = emptyList(),
    val profile: String = "",
    val description: String = ""
)
