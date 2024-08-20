package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.domain.entity.member.Member

data class ProfileUiModel(
    val id: Long,
    val profile: String,
    val username: String,
    val stampCnt: Int,
    val likesCnt: Int,
    val filterViewCnt: Int,
    val reviewCnt: Int
) {
    companion object {
        fun toUiModel(data: Member): ProfileUiModel {
            return ProfileUiModel(
                id = data.id,
                profile = data.profile,
                username = data.username,
                stampCnt = data.stampCnt,
                likesCnt = data.likesCnt,
                filterViewCnt = data.filterViewCnt,
                reviewCnt = data.reviewCnt
            )
        }
    }
}