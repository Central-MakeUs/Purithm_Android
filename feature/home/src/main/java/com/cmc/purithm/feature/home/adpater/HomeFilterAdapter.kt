package com.cmc.purithm.feature.home.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.home.HomeViewModel
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.ListFilterBinding

class HomeFilterAdapter(
    private val viewModel : HomeViewModel
) : PagingDataAdapter<HomeFilterUiModel, HomeFilterAdapter.HomeFilterViewHolder>(HomeFilterDiffUtil()) {

    inner class HomeFilterViewHolder(private val binding: ListFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeFilterUiModel?) {
            data?.run {
                with(binding) {
                    model = data
                    root.setOnClickListener {
                        viewModel.clickFilterItem(data.id, data.canAccess)
                    }

                    btnLike.setOnClickListener {
                        data.liked = !data.liked
                        if(data.liked){
                            viewModel.deleteFilterLike(data.id)
                        } else {
                            viewModel.setFilterLike(data.id)
                        }
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