package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.design.component.view.FilterPremiumView
import com.cmc.purithm.domain.entity.filter.Filter

data class FilterLikeUiModel(
    val id: Long,
    val membership: FilterPremiumView.FilterMembership,
    val name: String,
    val thumbnailUrl: String,
    val photographerName: String,
    val likes: Int,
    val canAccess: Boolean,
    var liked: Boolean,
    val viewOs : String
) {
    companion object {
        fun toUiModel(data: Filter) = FilterLikeUiModel(
            id = data.id,
            membership = generateMembership(data.memberShip),
            name = data.name,
            thumbnailUrl = data.thumbnail,
            photographerName = data.photographerName,
            likes = data.likes,
            canAccess = data.canAccess,
            liked = data.liked,
            viewOs = data.viewOs
        )

        private fun generateMembership(membership: String) = when (membership) {
            "BASIC" -> FilterPremiumView.FilterMembership.BASIC
            "PREMIUM" -> FilterPremiumView.FilterMembership.PREMIUM
            "PREMIUM_PLUS" -> FilterPremiumView.FilterMembership.PREMIUM_PLUS
            else -> throw IllegalArgumentException("Invalid membership")
        }
    }
}
