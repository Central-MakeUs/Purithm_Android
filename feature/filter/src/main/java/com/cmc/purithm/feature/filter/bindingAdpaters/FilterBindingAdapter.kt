package com.cmc.purithm.feature.filter.bindingAdpaters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.feature.filter.R

object FilterBindingAdapter {
    
    @BindingAdapter("reviewCount")
    @JvmStatic
    fun TextView.setReviewCount(reviewCount : Int){
        text = String.format(context.getString(R.string.content_review_count), reviewCount)
    }
}