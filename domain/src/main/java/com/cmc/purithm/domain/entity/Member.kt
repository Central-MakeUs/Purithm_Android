package com.cmc.purithm.domain.entity

data class Member(
    val id: Long,
    val email: String,
    val nickname: String,
    val profile: String,
    val username: String
)