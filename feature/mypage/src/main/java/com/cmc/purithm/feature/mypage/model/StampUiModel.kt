package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.domain.entity.filter.FilterHistory

data class StampUiModel(
    val stampCount: Int,
    val stampHistoryList: List<StampHistory>
) {
    data class StampHistory(
        val date: String,
        val stampItemList: List<StampHistoryItem>
    ) {
        data class StampHistoryItem(
            val id: Long,
            val filterId: Long,
            val memberShip: String,
            val filterName: String,
            val artistName: String,
            val reviewId: Long,
            val thumbnail: String,
        )
    }

    companion object {
        fun toUiModel(item: FilterHistory): StampUiModel {
            return StampUiModel(
                stampCount = item.totalCount,
                stampHistoryList = item.list.map { stampListItem ->
                    StampHistory(
                        date = stampListItem.date,
                        stampItemList = stampListItem.filters.map { stampItem ->
                            StampHistory.StampHistoryItem(
                                id = stampItem.userMetaData.writeReviewId,
                                memberShip = stampItem.memberShip,
                                filterName = stampItem.name,
                                artistName = stampItem.photographerName,
                                reviewId = stampItem.userMetaData.writeReviewId,
                                thumbnail = stampItem.thumbnail,
                                filterId = stampItem.id
                            )
                        }
                    )
                }
            )
        }
    }
}