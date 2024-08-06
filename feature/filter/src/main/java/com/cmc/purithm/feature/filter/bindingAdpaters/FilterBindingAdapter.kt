package com.cmc.purithm.feature.filter.bindingAdpaters

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.feature.filter.R

object FilterBindingAdapter {
    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic
    @BindingAdapter("pureDegree")
    fun TextView.setReview(pureDegree: Int) {
        val (imgRes, textColor) = when (pureDegree) {
            100 -> Pair(
                com.cmc.purithm.design.R.drawable.ic_review_100,
                com.cmc.purithm.design.R.color.purple_500
            )

            80 -> Pair(
                com.cmc.purithm.design.R.drawable.ic_review_80,
                com.cmc.purithm.design.R.color.purple_400
            )

            60 -> Pair(
                com.cmc.purithm.design.R.drawable.ic_review_60,
                com.cmc.purithm.design.R.color.blue_300
            )

            40 -> Pair(
                com.cmc.purithm.design.R.drawable.ic_review_40,
                com.cmc.purithm.design.R.color.blue_200
            )

            20 -> Pair(
                com.cmc.purithm.design.R.drawable.ic_review_20,
                com.cmc.purithm.design.R.color.blue_100
            )

            else -> throw IllegalArgumentException("pureDegree must be 100, 80, 60, 40, 20")
        }
        text = "${pureDegree}%"
        setTextColor(context.getColor(textColor))
        val imgDrawable = context.resources.getDrawable(imgRes, null)
        setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null)
    }


    @JvmStatic
    @BindingAdapter("reviewBackground")
    fun ImageView.setReviewBackground(pureDegree: Int) {
        setBackgroundResource(
            when (pureDegree) {
                in 100 downTo 81 -> R.drawable.bg_review_100
                in 80 downTo 61 -> R.drawable.bg_review_80
                in 60 downTo 41 -> R.drawable.bg_review_60
                in 40 downTo 21 -> R.drawable.bg_review_40
                else -> R.drawable.bg_review_20
            }
        )
    }

    @JvmStatic
    @BindingAdapter("reviewIntensityBackground")
    fun ImageView.setReviewIntensityBackground(pureDegree: Int) {
        setBackgroundResource(
            when (pureDegree) {
                in 100 downTo 81 -> R.drawable.bg_review_intensity_100
                in 80 downTo 61 -> R.drawable.bg_review_intensity_80
                in 60 downTo 41 -> R.drawable.bg_review_intensity_60
                in 40 downTo 21 -> R.drawable.bg_review_intensity_40
                else -> R.drawable.bg_review_intensity_20
            }
        )
    }

    @JvmStatic
    @BindingAdapter("reviewCount")
    fun TextView.setReviewCount(reviewCount : Int){
        text = String.format(context.getString(R.string.content_review_count), reviewCount)
    }
}