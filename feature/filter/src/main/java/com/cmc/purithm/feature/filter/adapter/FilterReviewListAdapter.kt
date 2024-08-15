package com.cmc.purithm.feature.filter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.ListFilterReviewBinding
import com.cmc.purithm.feature.filter.model.FilterReviewItemUiModel
import com.cmc.purithm.feature.filter.viewmodel.FilterReviewViewModel

class FilterReviewListAdapter(
    private val clickEvent : (Long) -> Unit
) :
    ListAdapter<FilterReviewItemUiModel, FilterReviewListAdapter.FilterReviewViewHolder>(
        FilterReviewDiffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterReviewViewHolder {
        return FilterReviewViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_filter_review,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterReviewViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FilterReviewViewHolder(private val binding: ListFilterReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilterReviewItemUiModel) {
            Log.d(TAG, "bind: data = ${data}")
            binding.root.setOnClickListener {
                clickEvent(data.id)
            }
            binding.data = data
            binding.viewReviewIntensity.setReviewIntensity(data.pureDegree)
        }
    }

    private object FilterReviewDiffUtil : DiffUtil.ItemCallback<FilterReviewItemUiModel>() {
        override fun areItemsTheSame(
            oldItem: FilterReviewItemUiModel,
            newItem: FilterReviewItemUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FilterReviewItemUiModel,
            newItem: FilterReviewItemUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
    companion object {
        private const val TAG = "FilterReviewListAdapter"
    }
}