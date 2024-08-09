package com.cmc.purithm.common.bindingAdapters

import android.widget.CheckBox
import android.widget.ImageButton
import androidx.databinding.BindingAdapter

object ButtonBindingAdapters {
    @BindingAdapter("like")
    @JvmStatic
    fun ImageButton.checkLiked(like : Boolean){
        setImageResource(if (like) com.cmc.purithm.design.R.drawable.ic_like_pressed else com.cmc.purithm.design.R.drawable.ic_like_unpressed)
    }
}