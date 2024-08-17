package com.cmc.purithm.feature.feed.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.feed.R
import com.cmc.purithm.feature.feed.databinding.ListFeedBinding
import com.cmc.purithm.feature.feed.model.FeedUiModel
import com.cmc.purithm.feature.feed.viewModel.FeedViewModel

class FeedAdapter(
    private val viewModel: FeedViewModel,
    private val fragmentActivity: FragmentActivity
) : ListAdapter<FeedUiModel, FeedAdapter.FeedViewHolder>(FeedDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_feed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FeedViewHolder(private val binding: ListFeedBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private lateinit var feedPictureAdapter : FeedPictureAdapter
        fun bind(data: FeedUiModel) {
            with(binding) {
                this.data = data
                if(!::feedPictureAdapter.isInitialized){
                    feedPictureAdapter = FeedPictureAdapter(fragmentActivity, data.pictures)
                    vpPicture.isUserInputEnabled = true
                    vpPicture.adapter = feedPictureAdapter
                }
                if (data.pictures.size > 1) {
                    indicatorPicture.setViewPager(vpPicture)
                } else {
                    indicatorPicture.visibility = View.GONE
                }
                viewReviewIntensity.setReviewIntensity(data.pureDegree)
                viewFilterChip.setInitInfo(data.filterThumbnail, data.filterName, clickEvent = {
                    viewModel.clickFeedFilter(data.filterId)
                })
            }
        }
    }

    private object FeedDiffUtil : DiffUtil.ItemCallback<FeedUiModel>() {
        override fun areItemsTheSame(oldItem: FeedUiModel, newItem: FeedUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FeedUiModel, newItem: FeedUiModel): Boolean {
            return oldItem == newItem

        }
    }

    companion object {
        private const val TAG = "FeedAdapter"
    }
}