package com.cmc.purithm.domain.entity.member

data class Member(
    val id: Long = 0L,
    val memberMetaData: MemberMetaData? = null,
    val account : Account? = null,
    val email: String = "",
    val profile: String = "",
    val username: String = "",
    val stampCnt : Int = 0,
    val likesCnt : Int = 0,
    val filterViewCnt : Int = 0,
    val reviewCnt : Int = 0
)