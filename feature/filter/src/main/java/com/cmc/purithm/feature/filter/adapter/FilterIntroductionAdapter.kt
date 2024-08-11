package com.cmc.purithm.feature.filter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.domain.entity.filter.FilterIntroduction
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.ListFilterIntroductionBinding

class FilterIntroductionAdapter :
    ListAdapter<FilterIntroduction, FilterIntroductionAdapter.FilterIntroductionViewHolder>(
        FilterIntroductionDiffUtil
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterIntroductionViewHolder {
        return FilterIntroductionViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_filter_introduction,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterIntroductionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FilterIntroductionViewHolder(private val binding: ListFilterIntroductionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterIntroduction) {
            Log.d(TAG, "bind: item = ${item}")
            binding.data = item
        }
    }

    private object FilterIntroductionDiffUtil : DiffUtil.ItemCallback<FilterIntroduction>() {
        override fun areItemsTheSame(
            oldItem: FilterIntroduction,
            newItem: FilterIntroduction
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: FilterIntroduction,
            newItem: FilterIntroduction
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "FilterIntroductionAdapt"
    }
}