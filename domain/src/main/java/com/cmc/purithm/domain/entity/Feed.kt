package com.cmc.purithm.domain.entity

data class Feed(
    val id : Long,
    val photographerId : Long,
    val filterId : Long,
    val content : String
)
