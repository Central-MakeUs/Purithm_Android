package com.cmc.purithm.domain.entity.filter

import com.cmc.purithm.domain.entity.member.MemberMetaData

data class Filter(
    val id: Long = 0L,
    val memberShip: String = "",
    val name: String = "",
    val userMetaData: MemberMetaData = MemberMetaData(),
    val thumbnail: String = "",
    val photographerId: Long = 0L,
    val photographerName: String = "",
    val photographerProfile: String = "",
    val likes: Int = 0,
    val canAccess: Boolean = false,
    val liked: Boolean = false,
    val pureDegree: Int = 0,
    val filterValue: FilterValue? = null,
    val pictures: List<FilterImg> = emptyList(),
    val filterIntroduction: List<FilterIntroduction> = emptyList(),
    val description: String = "",
    val tag: List<String> = emptyList()
)