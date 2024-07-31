package com.cmc.purithm.domain.entity

data class Photographer(
    val id: Long,
    val thumbnails: List<String>,
    val profile: String,
    val description: String
)
