package com.cmc.purithm.feature.artist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.databinding.ListArtistFilterBinding
import com.cmc.purithm.feature.artist.model.ArtistFilterUiModel
import com.cmc.purithm.feature.artist.viewmodel.ArtistFilterViewModel

class ArtistFilterAdapter (
    private val viewModel : ArtistFilterViewModel
) : PagingDataAdapter<ArtistFilterUiModel, ArtistFilterAdapter.ArtistFilterViewHolder>(ArtistFilterDiffUtil){

    inner class ArtistFilterViewHolder(private val binding: ListArtistFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ArtistFilterUiModel?) {
            data?.run {
                with(binding) {
                    model = data
                    root.setOnClickListener {
                        viewModel.clickFilterItem(data.id, data.canAccess)
                    }

                    btnLike.setOnClickListener {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArtistFilterViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_artist_filter, parent, false
        )
    )

    override fun onBindViewHolder(holder: ArtistFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object ArtistFilterDiffUtil : DiffUtil.ItemCallback<ArtistFilterUiModel>() {
        override fun areItemsTheSame(
            oldItem: ArtistFilterUiModel,
            newItem: ArtistFilterUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArtistFilterUiModel,
            newItem: ArtistFilterUiModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}