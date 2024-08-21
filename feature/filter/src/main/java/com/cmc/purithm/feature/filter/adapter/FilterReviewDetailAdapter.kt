package com.cmc.purithm.feature.filter.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.ListFilterReviewDetailBinding
import com.cmc.purithm.feature.filter.model.FilterReviewItemUiModel

class FilterReviewDetailAdapter(
    private val fragmentActivity: FragmentActivity
) :
    ListAdapter<FilterReviewItemUiModel, FilterReviewDetailAdapter.FilterReviewDetailViewHolder>(
        FilterReviewDetailDiffUtil
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterReviewDetailViewHolder {
        return FilterReviewDetailViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_filter_review_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterReviewDetailViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun getReviewItemWithPosition(reviewId: Long): Int {
        return currentList.indexOfFirst { it.id == reviewId }
    }

    inner class FilterReviewDetailViewHolder(
        private val binding: ListFilterReviewDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterReviewItemUiModel) {
            Log.d(TAG, "bind: data = ${item}")
            with(binding) {
                data = item
                viewReviewIntensity.setReviewIntensity(item.pureDegree)
                vpPicture.adapter =
                    FilterReviewPictureAdapter(fragmentActivity, item.pictures)
                if(item.pictures.size > 1){
                    indicatorOnboarding.setViewPager(vpPicture)
                } else {
                    indicatorOnboarding.visibility = View.GONE
                    imgListBackground.visibility = View.GONE
                }
            }
        }
    }

    private object FilterReviewDetailDiffUtil : DiffUtil.ItemCallback<FilterReviewItemUiModel>() {
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
        private const val TAG = "FilterReviewDetailAdapt"
    }
}