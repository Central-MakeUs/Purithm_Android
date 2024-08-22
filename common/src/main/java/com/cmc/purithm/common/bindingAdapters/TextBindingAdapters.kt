package com.cmc.purithm.common.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

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
}