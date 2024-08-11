package com.cmc.purithm.domain.entity.review

data class ReviewItem (
    val id : Long = 0L,
    val pureDegree : Int = 0,
    val userName : String = "",
    val pictures : List<String> = emptyList(),
    val createdAt : String = ""
)