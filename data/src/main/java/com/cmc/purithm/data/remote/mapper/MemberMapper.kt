package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.filter.FilterHistoryResponseDto
import com.cmc.purithm.data.remote.dto.member.GetStampResponseDto
import com.cmc.purithm.data.remote.dto.member.MemberAccountResponseDto
import com.cmc.purithm.data.remote.dto.member.MemberResponseDto
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.domain.entity.filter.FilterHistory
import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member
import com.cmc.purithm.domain.entity.member.MemberMetaData

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

@JvmName("stampToFilterHistory")
internal fun BaseResponse<GetStampResponseDto>.toDomain(): FilterHistory {
    val data = data ?: throw NullPointerException("data is null")
    return FilterHistory(
        totalCount = data.totalCount,
        list = data.list.map { stampList ->
            FilterHistory.FilterHistoryItem(
                date = stampList.date,
                filters = stampList.stamps.map { stampItem ->
                    Filter(
                        id = stampItem.filterId,
                        name = stampItem.filterName,
                        photographerName = stampItem.photographer,
                        thumbnail = stampItem.thumbnail,
                        memberShip = stampItem.membership,
                        userMetaData = MemberMetaData(
                            hasViewed = true,
                            hasReview = true,
                            writeReviewId = stampItem.reviewId
                        )
                    )
                }
            )
        }
    )
}

@JvmName("filterHistoryToFilterHistory")
internal fun BaseResponse<FilterHistoryResponseDto>.toDomain(): FilterHistory {
    val data = data ?: throw NullPointerException("data is null")
    return FilterHistory(
        totalCount = data.totalCount,
        list = data.list.map { list ->
            FilterHistory.FilterHistoryItem(
                date = list.date,
                filters = list.filters.map { item ->
                    Filter(
                        id = item.filterId,
                        name = item.filterName,
                        photographerName = item.photographer,
                        thumbnail = item.thumbnail,
                        memberShip = item.membership,
                        userMetaData = MemberMetaData(
                            hasViewed = true,
                            hasReview = item.hasReview,
                            writeReviewId = item.reviewId ?: 0L
                        )
                    )
                }
            )
        }
    )
}
