package com.cmc.purithm.feature.review.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.ViewTermOfReviewContentBinding

class TermOfReviewTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){
    private val binding by lazy { initView(context) }

    private fun initView(context: Context) : ViewTermOfReviewContentBinding {
        val inflater = LayoutInflater.from(context)
        return DataBindingUtil.inflate(inflater, R.layout.view_term_of_review_content, this, true)
    }

    fun setMainText(text : String) {
        binding.tvMainText.text = text
    }

    // FIXME : 유동적으로 가능하게
    fun setSubText(list : List<String>){
        val text1 = list[0]
        val text2 = list[1]
        val text3 = list[2]
        val text4 = list[3]
        with(binding){
            tvSubText1.text = text1
            tvSubText1.visibility = View.VISIBLE
            tvSubText2.text = text2
            tvSubText2.visibility = View.VISIBLE
            tvSubText3.text = text3
            tvSubText3.visibility = View.VISIBLE
            tvSubText4.text = text4
            tvSubText4.visibility = View.VISIBLE
        }
    }
}