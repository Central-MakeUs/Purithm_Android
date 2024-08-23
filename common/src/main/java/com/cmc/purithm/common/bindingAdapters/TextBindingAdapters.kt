package com.cmc.purithm.common.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.design.component.view.FilterPremiumView

object TextBindingAdapters {
    @BindingAdapter("date")
    @JvmStatic
    fun TextView.setDate(date : String?) {
        text = date?.split("T")?.get(0)?.replace("-", ".") ?: ""
    }

    @BindingAdapter("made_by")
    @JvmStatic
    fun TextView.setMadeBy(madeBy : String?) {
        text = "Made by $madeBy"
    }

    @JvmStatic
    @BindingAdapter("membership")
    fun TextView.setMemberShip(membership: FilterPremiumView.FilterMembership) {
        val (text, background) = when (membership) {
            FilterPremiumView.FilterMembership.BASIC -> "" to com.cmc.purithm.design.R.color.transparent
            FilterPremiumView.FilterMembership.PREMIUM -> "Premium" to com.cmc.purithm.design.R.drawable.bg_text_filter_premium
            FilterPremiumView.FilterMembership.PREMIUM_PLUS -> "Premium +" to com.cmc.purithm.design.R.drawable.bg_text_filter_premium_plus
        }
        setText(text)
        setBackgroundResource(background)
    }
}