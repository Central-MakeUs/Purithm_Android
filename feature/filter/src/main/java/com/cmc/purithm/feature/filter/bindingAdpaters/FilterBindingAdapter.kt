package com.cmc.purithm.feature.filter.bindingAdpaters

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.feature.filter.R

object FilterBindingAdapter {
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    @BindingAdapter("pureDegree")
    fun TextView.setReview(pureDegree: Int) {
        val (imgRes,textColor) = when (pureDegree) {
            100 -> Pair(com.cmc.purithm.design.R.drawable.ic_review_100, com.cmc.purithm.design.R.color.purple_500)
            80 -> Pair(com.cmc.purithm.design.R.drawable.ic_review_80, com.cmc.purithm.design.R.color.purple_400)
            60 -> Pair(com.cmc.purithm.design.R.drawable.ic_review_60, com.cmc.purithm.design.R.color.blue_300)
            40 -> Pair(com.cmc.purithm.design.R.drawable.ic_review_40, com.cmc.purithm.design.R.color.blue_200)
            20 -> Pair(com.cmc.purithm.design.R.drawable.ic_review_20, com.cmc.purithm.design.R.color.blue_100)
            else -> throw IllegalArgumentException("pureDegree must be 100, 80, 60, 40, 20")
        }
        text = "${pureDegree}%"
        setTextColor(context.getColor(textColor))
        val imgDrawable = context.resources.getDrawable(imgRes, null)
        setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null)
    }
}