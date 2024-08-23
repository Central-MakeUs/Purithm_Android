package com.cmc.purithm.feature.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ListStampFilterBinding
import com.cmc.purithm.feature.mypage.model.StampUiModel

class StampFilterListAdapter(
    private val reviewHistoryClickEvent : StampListAdapter.StampListClickListener
) : ListAdapter<StampUiModel.StampHistory.StampHistoryItem, StampFilterListAdapter.StampFilterViewHolder>(StampFilterDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampFilterViewHolder {
        return StampFilterViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_stamp_filter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StampFilterViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class StampFilterViewHolder(private val binding: ListStampFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StampUiModel.StampHistory.StampHistoryItem){
            binding.data = item
            binding.tvReviewHistory.setOnClickListener {
                reviewHistoryClickEvent.onReviewHistoryClick(
                    reviewId = item.reviewId,
                    filterId = item.id,
                    filterName = item.filterName,
                    thumbnail = item.thumbnail
                )
            }
        }
    }

    private object StampFilterDiffUtil :
        DiffUtil.ItemCallback<StampUiModel.StampHistory.StampHistoryItem>() {
        override fun areItemsTheSame(
            oldItem: StampUiModel.StampHistory.StampHistoryItem,
            newItem: StampUiModel.StampHistory.StampHistoryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StampUiModel.StampHistory.StampHistoryItem,
            newItem: StampUiModel.StampHistory.StampHistoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}