package com.cmc.purithm.feature.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.adapter.diffUtil.HistoryDiffUtil
import com.cmc.purithm.feature.mypage.adapter.listener.HistoryClickListener
import com.cmc.purithm.feature.mypage.databinding.ListHistoryBinding
import com.cmc.purithm.feature.mypage.model.HistoryUiModel

class StampListAdapter(
    private val historyClickEvent: HistoryClickListener
) :
    ListAdapter<HistoryUiModel.History, StampListAdapter.StampViewHolder>(HistoryDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampViewHolder {
        return StampViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_history,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StampViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class StampViewHolder(private val binding: ListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryUiModel.History) {
            val adapter = StampFilterListAdapter(historyClickEvent)
            binding.date = item.date
            binding.listStampFilter.adapter = adapter
            adapter.submitList(item.stampItemList)
        }
    }
}