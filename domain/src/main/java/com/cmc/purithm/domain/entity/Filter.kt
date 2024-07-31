package com.cmc.purithm.domain.entity

data class Filter(
    val id: Long,
    // FIXME : Enum or Int
    val membership: Int,
    val photographerId: Long,
    val thumbnail: String,
    val pureDegree: Int,
    val pictures: List<String>,
    val name: String,
    val likes: Int,
    // FIXME : Long or Date
    val createAt: Long
)