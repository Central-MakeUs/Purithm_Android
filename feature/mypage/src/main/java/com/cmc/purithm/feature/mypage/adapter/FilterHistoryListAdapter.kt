package com.cmc.purithm.feature.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.adapter.diffUtil.HistoryDiffUtil
import com.cmc.purithm.feature.mypage.adapter.diffUtil.HistoryItemDiffUtil
import com.cmc.purithm.feature.mypage.adapter.listener.FilterHistoryClickListener
import com.cmc.purithm.feature.mypage.adapter.listener.HistoryClickListener
import com.cmc.purithm.feature.mypage.databinding.ListFilterHistoryBinding
import com.cmc.purithm.feature.mypage.databinding.ListStampHistoryBinding
import com.cmc.purithm.feature.mypage.model.HistoryUiModel

class FilterHistoryListAdapter(
    private val historyClickListener: HistoryClickListener,
    private val filterHistoryClickListener: FilterHistoryClickListener
) : ListAdapter<HistoryUiModel.History.HistoryItem, FilterHistoryListAdapter.FilterHistoryViewHolder>(
    HistoryItemDiffUtil
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHistoryViewHolder {
        return FilterHistoryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_filter_history,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterHistoryViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FilterHistoryViewHolder(private val binding: ListFilterHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryUiModel.History.HistoryItem) {
            with(binding) {
                data = item
                Log.d(TAG, "bind: reviewId : ${item.reviewId}")
                Log.d(TAG, "bind: filterName : ${item.filterName}")
                imgFilterThumbnail.setOnClickListener {
                    historyClickListener.onStampThumbClick(
                        filterId = item.filterId,
                        item.viewOs
                    )
                }

                tvReviewHistory.text = if(item.reviewId != 0L){
                    "남긴 후기"
                } else {
                    "후기 남기기"
                }

                tvReviewHistory.setOnClickListener {
                    if (item.reviewId != 0L) {
                        historyClickListener.onReviewHistoryClick(
                            reviewId = item.reviewId,
                            filterId = item.id,
                            filterName = item.filterName,
                            thumbnail = item.thumbnail
                        )
                    } else {
                        filterHistoryClickListener.onReviewWriteClick(
                            item.filterId,
                            item.thumbnail,
                            item.filterName
                        )
                    }
                }
                tvFilterValue.setOnClickListener {
                    filterHistoryClickListener.onFilterValueClick(item.filterId, item.viewOs)
                }
            }
        }
    }


    companion object {
        private const val TAG = "FilterHistoryListAdapte"
    }
}