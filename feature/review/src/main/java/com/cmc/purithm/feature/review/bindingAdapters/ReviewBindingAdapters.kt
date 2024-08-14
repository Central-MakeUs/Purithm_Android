package com.cmc.purithm.feature.review.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.design.R

object ReviewBindingAdapters {
    @JvmStatic
    @BindingAdapter("review_percentage")
    fun TextView.setReviewPercentage(pureDegree : Int) {
        text = "${pureDegree}%"
    }
}