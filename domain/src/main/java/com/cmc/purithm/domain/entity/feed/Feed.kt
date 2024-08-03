package com.cmc.purithm.domain.entity.feed

data class Feed(
    val id : Long,
    val photographerId : Long,
    val filterId : Long,
    val content : String
)
