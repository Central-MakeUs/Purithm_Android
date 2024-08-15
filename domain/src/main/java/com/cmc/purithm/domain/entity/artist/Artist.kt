package com.cmc.purithm.domain.entity.artist

data class Artist(
    val id: Long = 0L,
    val name : String = "",
    val profileImg : String = "",
    val description : String = "",
    val pictures : List<String> = emptyList(),
    val createdAt : String = ""
)
