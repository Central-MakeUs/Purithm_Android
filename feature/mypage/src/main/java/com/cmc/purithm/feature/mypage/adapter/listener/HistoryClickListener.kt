package com.cmc.purithm.feature.mypage.adapter.listener

interface HistoryClickListener {
    fun onReviewHistoryClick(
        reviewId: Long,
        filterId: Long,
        filterName: String,
        thumbnail: String
    )

    fun onStampThumbClick(filterId: Long)
}

