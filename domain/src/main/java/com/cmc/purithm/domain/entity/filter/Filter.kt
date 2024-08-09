package com.cmc.purithm.domain.entity.filter

data class Filter(
    val id : Long = 0L,
    val memberShip : String = "",
    val name : String = "",
    val thumbnail : String = "",
    val photographerId : Long = 0L,
    val photographerName : String = "",
    val likes : Int = 0,
    val canAccess : Boolean = false,
    val liked : Boolean = false,
    val pureDegree : Int = 0,
    val pictures : List<FilterImg> = emptyList()
)