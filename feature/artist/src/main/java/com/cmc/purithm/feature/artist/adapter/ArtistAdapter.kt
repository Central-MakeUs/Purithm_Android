package com.cmc.purithm.feature.artist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.databinding.ListArtistBinding
import com.cmc.purithm.feature.artist.model.ArtistUiModel
import com.cmc.purithm.feature.artist.viewmodel.ArtistViewModel

class ArtistAdapter(
    private val viewModel: ArtistViewModel
) : ListAdapter<ArtistUiModel, ArtistAdapter.ArtistViewHolder>(ArtistDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        return ArtistViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_artist,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ArtistViewHolder(
        private val binding: ListArtistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private fun setDividerVisibility(size: Int) {
            with(binding) {
                when (size) {
                    3 -> viewDividerBottom.visibility = View.GONE
                    2 -> viewDividerTop.visibility = View.GONE
                    1 -> viewDividerHorizontal.visibility = View.GONE
                }
            }
        }

        fun bind(data: ArtistUiModel) {
            with(binding) {
                vm = viewModel
                this.data = data
                setDividerVisibility(data.pictures.size)
            }
        }
    }

    private object ArtistDiffUtil : DiffUtil.ItemCallback<ArtistUiModel>() {
        override fun areItemsTheSame(oldItem: ArtistUiModel, newItem: ArtistUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ArtistUiModel, newItem: ArtistUiModel): Boolean {
            return oldItem == newItem
        }
    }
}