package com.cmc.purithm.feature.filter.bindingAdpaters

import android.view.View
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

    @BindingAdapter("guideVisibility")
    @JvmStatic
    fun View.setGuideVisibility(isVisible : Boolean){
        visibility = if(isVisible) {
            bringToFront()
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @BindingAdapter(value = ["noTextVisibility", "guideVisibility"], requireAll = true)
    @JvmStatic
    fun View.setButtonVisibility(noTextVisibility : Boolean, guideVisibility: Boolean){
        visibility = if(!noTextVisibility && !guideVisibility) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}