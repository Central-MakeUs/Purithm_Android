package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.member.MemberAccountResponseDto
import com.cmc.purithm.data.remote.dto.member.MemberResponseDto
import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member

internal fun BaseResponse<MemberResponseDto>.toDomain(): Member {
    val data = data ?: throw NullPointerException("data is null")
    return Member(
        id = data.id,
        username = data.name,
        profile = data.profile,
        stampCnt = data.stamp,
        likesCnt = data.likes,
        filterViewCnt = data.filterViewCount,
        reviewCnt = data.reviews
    )
}

internal fun BaseResponse<MemberAccountResponseDto>.toDomain(): Account {
    val data = data ?: throw NullPointerException("data is null")
    return Account(
        joinType = data.provider,
        email = data.email,
        joinDate = data.joinDate
    )
}