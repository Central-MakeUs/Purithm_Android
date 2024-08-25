package com.cmc.purithm.feature.mypage.model

import com.cmc.purithm.domain.entity.filter.FilterHistory

data class HistoryUiModel(
    val count: Int = 0,
    val historyList: List<History> = emptyList()
) {
    data class History(
        val date: String,
        val stampItemList: List<HistoryItem>
    ) {
        data class HistoryItem(
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
        fun toUiModel(item: FilterHistory): HistoryUiModel {
            return HistoryUiModel(
                count = item.totalCount,
                historyList = item.list.map { listItem ->
                    History(
                        date = listItem.date,
                        stampItemList = listItem.filters.map { item ->
                            History.HistoryItem(
                                id = item.userMetaData.writeReviewId,
                                memberShip = item.memberShip,
                                filterName = item.name,
                                artistName = item.photographerName,
                                reviewId = item.userMetaData.writeReviewId,
                                thumbnail = item.thumbnail,
                                filterId = item.id
                            )
                        }
                    )
                }
            )
        }
    }
}