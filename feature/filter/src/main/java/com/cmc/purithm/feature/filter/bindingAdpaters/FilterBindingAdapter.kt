package com.cmc.purithm.feature.filter.bindingAdpaters

import android.view.View
import android.widget.Button
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

    @JvmStatic
    @BindingAdapter(value = ["hasReview", "hasViewed"], requireAll = true)
    fun Button.setButtonText(hasReview : Boolean, hasViewed : Boolean){
        text = if(hasReview){
            "내가 남긴 후기 보기"
        } else if(hasViewed) {
            "후기 남기고 스탬프 받기"
        } else {
            "필터값 보기"
        }
    }
}