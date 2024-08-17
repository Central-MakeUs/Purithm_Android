package com.cmc.purithm.domain.entity.review

import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.entity.member.MemberMetaData

data class Review(
    val avg: Int = 0,
    val memberMetaData: MemberMetaData? = null,
    val reviews: List<ReviewItem> = emptyList()
)