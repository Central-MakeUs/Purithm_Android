package com.cmc.purithm.domain.entity.review

data class Review (
    val id : Long,
    val userId : Long,
    val filterId : Long,
    val pictures : List<String>
)