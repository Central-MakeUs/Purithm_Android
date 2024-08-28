package com.cmc.purithm.feature.mypage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ListReviewHistoryBinding
import com.cmc.purithm.feature.mypage.model.ReviewHistoryUiModel
import com.cmc.purithm.feature.mypage.viewmodel.ReviewHistoryViewModel

class ReviewHistoryAdapter(
    private val fragmentActivity: FragmentActivity,
    private val viewModel: ReviewHistoryViewModel
) : ListAdapter<ReviewHistoryUiModel, ReviewHistoryAdapter.FeedViewHolder>(ReviewHistoryDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_review_history,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FeedViewHolder(private val binding: ListReviewHistoryBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        private lateinit var reviewHistoryPictureAdapter: ReviewHistoryPictureAdapter
        fun bind(data: ReviewHistoryUiModel) {
            with(binding) {
                this.data = data
                if (!::reviewHistoryPictureAdapter.isInitialized) {
                    reviewHistoryPictureAdapter =
                        ReviewHistoryPictureAdapter(fragmentActivity, data.pictures)
                    vpPicture.isUserInputEnabled = true
                    vpPicture.adapter = reviewHistoryPictureAdapter
                }
                binding.btnDelete.setOnClickListener {
                    viewModel.clickDeleteReview(data.id)
                }
                if (data.pictures.size > 1) {
                    indicatorPicture.setViewPager(vpPicture)
                } else {
                    indicatorPicture.visibility = View.GONE
                    imgListBackground.visibility = View.GONE
                }
                viewReviewIntensity.setReviewIntensity(data.pureDegree)
                viewFilterChip.setInitInfo(data.filterThumbnail, data.filterName, clickEvent = {
                    viewModel.clickFilter(data.filterId, data.viewOs)
                })
            }
        }
    }

    private object ReviewHistoryDiffUtil : DiffUtil.ItemCallback<ReviewHistoryUiModel>() {
        override fun areItemsTheSame(
            oldItem: ReviewHistoryUiModel,
            newItem: ReviewHistoryUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ReviewHistoryUiModel,
            newItem: ReviewHistoryUiModel
        ): Boolean {
            return oldItem == newItem

        }
    }

    companion object {
        private const val TAG = "ReviewHistoryAdapter"
    }
}