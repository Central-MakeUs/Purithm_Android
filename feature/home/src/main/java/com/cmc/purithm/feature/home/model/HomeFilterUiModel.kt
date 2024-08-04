package com.cmc.purithm.feature.home.model

import com.cmc.purithm.design.component.view.FilterPremiumView
import com.cmc.purithm.domain.entity.filter.Filter

data class HomeFilterUiModel(
    val id: Long,
    val membership: FilterPremiumView.FilterMembership,
    val name: String,
    val thumbnailUrl: String,
    val photographerName: String,
    val likes: Int,
    val canAccess: Boolean,
    val liked: Boolean
) {
    companion object {
        fun toUiModel(data: Filter) = HomeFilterUiModel(
            id = data.id,
            membership = generateMembership(data.memberShip),
            name = data.name,
            thumbnailUrl = data.thumbnail,
            photographerName = data.photographerName,
            likes = data.likes,
            canAccess = data.canAccess,
            liked = data.liked
        )

        private fun generateMembership(membership: String) = when (membership) {
            "BASIC" -> FilterPremiumView.FilterMembership.BASIC
            "PREMIUM" -> FilterPremiumView.FilterMembership.PREMIUM
            "PREMIUM_PLUS" -> FilterPremiumView.FilterMembership.PREMIUM_PLUS
            else -> throw IllegalArgumentException("Invalid membership")
        }
    }
}
