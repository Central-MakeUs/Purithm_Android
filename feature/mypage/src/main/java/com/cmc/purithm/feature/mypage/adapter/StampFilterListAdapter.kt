package com.cmc.purithm.feature.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.adapter.diffUtil.HistoryItemDiffUtil
import com.cmc.purithm.feature.mypage.adapter.listener.HistoryClickListener
import com.cmc.purithm.feature.mypage.databinding.ListStampHistoryBinding
import com.cmc.purithm.feature.mypage.model.HistoryUiModel

class StampFilterListAdapter(
    private val historyClickEvent : HistoryClickListener
) : ListAdapter<HistoryUiModel.History.HistoryItem, StampFilterListAdapter.StampFilterViewHolder>(HistoryItemDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampFilterViewHolder {
        return StampFilterViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_stamp_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StampFilterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class StampFilterViewHolder(private val binding: ListStampHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryUiModel.History.HistoryItem){
            binding.data = item
            binding.imgFilterThumbnail.setOnClickListener {
                historyClickEvent.onStampThumbClick(
                    filterId = item.filterId
                )
            }
            binding.tvReviewHistory.setOnClickListener {
                historyClickEvent.onReviewHistoryClick(
                    reviewId = item.reviewId,
                    filterId = item.id,
                    filterName = item.filterName,
                    thumbnail = item.thumbnail
                )
            }
        }
    }

}