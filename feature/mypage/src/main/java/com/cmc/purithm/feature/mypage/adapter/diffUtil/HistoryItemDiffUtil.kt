package com.cmc.purithm.feature.mypage.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.cmc.purithm.feature.mypage.model.HistoryUiModel

object HistoryItemDiffUtil :
        DiffUtil.ItemCallback<HistoryUiModel.History.HistoryItem>() {
        override fun areItemsTheSame(
            oldItem: HistoryUiModel.History.HistoryItem,
            newItem: HistoryUiModel.History.HistoryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HistoryUiModel.History.HistoryItem,
            newItem: HistoryUiModel.History.HistoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }