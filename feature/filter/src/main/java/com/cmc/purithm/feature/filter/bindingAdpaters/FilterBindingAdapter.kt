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

    @BindingAdapter("filterTag")
    @JvmStatic
    fun TextView.setFilterTag(tag : List<String>?){
        if(tag.isNullOrEmpty()){
            return
        }
        text = tag.joinToString(separator = " #", prefix = "#" )
    }

    @BindingAdapter("date")
    @JvmStatic
    fun TextView.setDate(date : String) {
        text = date.split("T")[0].replace("-", ".")
    }

    @BindingAdapter("percentage")
    @JvmStatic
    fun TextView.setPercentage(percentage : Int){
        text = "${percentage}%"
    }
}