package com.cmc.purithm.domain.entity.member

data class Member(
    val id: Long,
    val memberMetaData: MemberMetaData,
    val email: String,
    val nickname: String,
    val profile: String,
    val username: String
) {
}