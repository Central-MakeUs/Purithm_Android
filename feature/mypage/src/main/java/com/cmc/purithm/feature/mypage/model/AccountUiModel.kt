package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.domain.entity.member.Account
import com.cmc.purithm.domain.entity.member.Member

data class AccountUiModel(
    val joinType: String,
    val email: String,
    val joinDate: String
) {
    companion object {
        fun toUiModel(account : Account) : AccountUiModel {
            return AccountUiModel(
                joinDate = account.joinDate,
                email = account.email,
                joinType = account.joinType
            )
        }
    }
}