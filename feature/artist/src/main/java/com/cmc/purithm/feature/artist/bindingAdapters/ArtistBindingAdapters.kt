package com.cmc.purithm.feature.artist.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ArtistBindingAdapters {
    @JvmStatic
    @BindingAdapter("artist_thumbnail")
    fun ImageView.setArtistThumbnail(thumbnailUrl: String?) {
        if (thumbnailUrl == null) {
            visibility = View.GONE
            return
        }
        Glide.with(this)
            .load(thumbnailUrl)
            .placeholder(com.cmc.purithm.design.R.drawable.bg_image_placeholder)
            .into(this)
    }
}