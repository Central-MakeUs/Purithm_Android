package com.cmc.purithm.feature.home.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.ListFilterBinding

class HomeFilterAdapter(
    private val listener: HomeFilterItemClickListener
) : PagingDataAdapter<HomeFilterUiModel, HomeFilterAdapter.HomeFilterViewHolder>(HomeFilterDiffUtil()) {

    interface HomeFilterItemClickListener {
        fun onItemClick(id : Long)
        fun onLikeClick(id : Long)
    }

    inner class HomeFilterViewHolder(private val binding: ListFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeFilterUiModel?) {
            data?.run {
                with(binding) {
                    model = data
                    root.setOnClickListener {
                        listener.onItemClick(data.id)
                    }

                    btnLike.setOnClickListener {
                        listener.onLikeClick(data.id)
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