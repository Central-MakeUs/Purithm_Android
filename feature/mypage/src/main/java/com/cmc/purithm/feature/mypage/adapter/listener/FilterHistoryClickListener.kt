package com.cmc.purithm.feature.mypage.adapter.listener

interface FilterHistoryClickListener {
    fun onReviewWriteClick(filterId: Long, thumbnail: String, filterName: String)
    fun onFilterValueClick(filterId: Long, os : String)
}