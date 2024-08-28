package com.cmc.purithm.feature.mypage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ListFilterLikeBinding
import com.cmc.purithm.feature.mypage.model.FilterLikeUiModel
import com.cmc.purithm.feature.mypage.viewmodel.FilterLikeViewModel

class FilterLikeAdapter(private val viewModel: FilterLikeViewModel) :
    ListAdapter<FilterLikeUiModel, FilterLikeAdapter.FilterLikeViewHolder>(FilterLikeDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterLikeViewHolder {
        return FilterLikeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_filter_like,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilterLikeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FilterLikeViewHolder(private val binding: ListFilterLikeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var likeState = false
        fun bind(item: FilterLikeUiModel) {
            with(binding) {
                model = item
                likeState = item.liked
                Log.d(TAG, "bind: likeState = ${likeState}")
                btnLike.setOnClickListener {
                    Log.d(TAG, "btnLike click: likeState = ${likeState}")
                    if (likeState) {
                        Log.d(TAG, "btnLike click: deleteLikeFilter")
                        viewModel.deleteLikeFilter(item.id)
                        btnLike.setImageResource(com.cmc.purithm.design.R.drawable.ic_like_unpressed)
                        tvLikeCnt.text = (tvLikeCnt.text.toString().toInt() - 1).toString()
                    } else {
                        Log.d(TAG, "btnLike click: setLikeFilter")
                        viewModel.setLikeFilter(item.id)
                        btnLike.setImageResource(com.cmc.purithm.design.R.drawable.ic_like_pressed)
                        tvLikeCnt.text = (tvLikeCnt.text.toString().toInt() + 1).toString()
                    }
                    likeState = !likeState
                    Log.d(TAG, "bind: result = $likeState")
                }
                root.setOnClickListener {
                    viewModel.clickFilterItem(item.id, item.viewOs)
                }
            }
        }
    }

    private object FilterLikeDiffUtil : DiffUtil.ItemCallback<FilterLikeUiModel>() {
        override fun areItemsTheSame(
            oldItem: FilterLikeUiModel,
            newItem: FilterLikeUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FilterLikeUiModel,
            newItem: FilterLikeUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TAG = "FilterLikeAdapter"
    }
}