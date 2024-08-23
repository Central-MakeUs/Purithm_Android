package com.cmc.purithm.feature.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ListStampBinding
import com.cmc.purithm.feature.mypage.model.StampUiModel

class StampListAdapter(private val reviewHistoryClickEvent: StampListClickListener) :
    ListAdapter<StampUiModel.StampHistory, StampListAdapter.StampViewHolder>(StampDiffUtil) {

    interface StampListClickListener {
        fun onReviewHistoryClick(
            reviewId: Long,
            filterId: Long,
            filterName: String,
            thumbnail: String
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampViewHolder {
        return StampViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_stamp,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StampViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class StampViewHolder(private val binding: ListStampBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StampUiModel.StampHistory) {
            val adapter = StampFilterListAdapter(reviewHistoryClickEvent)
            binding.date = item.date
            binding.listStampFilter.adapter = adapter
            adapter.submitList(item.stampItemList)
        }
    }

    private object StampDiffUtil : DiffUtil.ItemCallback<StampUiModel.StampHistory>() {
        override fun areItemsTheSame(
            oldItem: StampUiModel.StampHistory,
            newItem: StampUiModel.StampHistory
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: StampUiModel.StampHistory,
            newItem: StampUiModel.StampHistory
        ): Boolean {
            return oldItem == newItem
        }
    }
}