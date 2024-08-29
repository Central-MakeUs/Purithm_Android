package com.cmc.purithm.feature.home.adpater

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.home.HomeViewModel
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purithm.feature.home.R
import com.cmc.purithm.feature.home.databinding.ListFilterBinding

class HomeFilterAdapter(
    private val viewModel: HomeViewModel
) : PagingDataAdapter<HomeFilterUiModel, HomeFilterAdapter.HomeFilterViewHolder>(HomeFilterDiffUtil()) {

    inner class HomeFilterViewHolder(private val binding: ListFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var likeState = false
        fun bind(data: HomeFilterUiModel?) {
            data?.run {
                with(binding) {
                    model = data
                    likeState = data.liked
                    root.setOnClickListener {
                        viewModel.clickFilterItem(data.id, data.canAccess)
                    }

                    btnLike.setOnClickListener {
                        if (likeState) {
                            viewModel.deleteFilterLike(data.id)
                            btnLike.setImageResource(com.cmc.purithm.design.R.drawable.ic_like_unpressed)
                            tvLikeCnt.text = (tvLikeCnt.text.toString().toInt() - 1).toString()
                        } else {
                            viewModel.setFilterLike(data.id)
                            btnLike.setImageResource(com.cmc.purithm.design.R.drawable.ic_like_pressed)
                            tvLikeCnt.text = (tvLikeCnt.text.toString().toInt() + 1).toString()
                        }
                        likeState = !likeState
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeFilterViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_filter, parent, false
        )
    )

    override fun onBindViewHolder(holder: HomeFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HomeFilterDiffUtil : DiffUtil.ItemCallback<HomeFilterUiModel>() {
        override fun areItemsTheSame(
            oldItem: HomeFilterUiModel,
            newItem: HomeFilterUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HomeFilterUiModel,
            newItem: HomeFilterUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}