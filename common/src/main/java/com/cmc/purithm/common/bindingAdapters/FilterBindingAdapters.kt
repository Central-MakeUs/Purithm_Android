package com.cmc.purithm.common.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.design.R
import com.cmc.purithm.design.component.view.FilterPremiumView

object FilterBindingAdapters {
    @JvmStatic
    @BindingAdapter(requireAll = true, value = ["membership", "canAccess"])
    fun FilterPremiumView.setMembership(
        membership: FilterPremiumView.FilterMembership,
        canAccess: Boolean
    ) {
        if (canAccess) {
            visibility = View.GONE
            return
        }
        setViewType(membership)
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
}