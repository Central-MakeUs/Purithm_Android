package com.cmc.purithm.common.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
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
}