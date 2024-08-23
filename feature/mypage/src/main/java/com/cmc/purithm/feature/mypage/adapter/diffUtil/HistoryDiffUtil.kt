package com.cmc.purithm.feature.mypage.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.cmc.purithm.feature.mypage.model.HistoryUiModel

object HistoryDiffUtil : DiffUtil.ItemCallback<HistoryUiModel.History>() {
        override fun areItemsTheSame(
            oldItem: HistoryUiModel.History,
            newItem: HistoryUiModel.History
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: HistoryUiModel.History,
            newItem: HistoryUiModel.History
        ): Boolean {
            return oldItem == newItem
        }
    }