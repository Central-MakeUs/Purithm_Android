package com.cmc.purithm.design.component.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewReviewIntensityBinding

class PurithmReviewIntensityView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        initView(context)
    }

    private fun initView(context: Context): ViewReviewIntensityBinding {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return DataBindingUtil.inflate(layoutInflater, R.layout.view_review_intensity, this, true)
    }

    @SuppressLint("SetTextI18n")
    fun setReviewIntensity(rating: ReviewRating) {
        val (imgRes, color) = getImgResByIntensity(rating) to getColorByIntensity(rating)
        with(binding){
            imgReview.setImageResource(imgRes)
            imgReview.setColorFilter(color)

            tvReviewIntensity.text = "${rating.intensity}%"
            tvReviewIntensity.setTextColor(color)
        }
    }

    private fun getImgResByIntensity(rating: ReviewRating) = when (rating) {
        ReviewRating.Outstanding, ReviewRating.Good -> R.drawable.ic_review_1
        ReviewRating.Satisfactory -> R.drawable.ic_review_2
        ReviewRating.NeedImprovement, ReviewRating.Poor -> R.drawable.ic_review_3
    }

    private fun getColorByIntensity(rating: ReviewRating) = when (rating) {
        ReviewRating.Outstanding -> R.color.purple_500
        ReviewRating.Good -> R.color.purple_400
        ReviewRating.Satisfactory -> R.color.blue_300
        ReviewRating.NeedImprovement -> R.color.blue_200
        ReviewRating.Poor -> R.color.blue_100
    }

    enum class ReviewRating(val intensity: Int) {
        Outstanding(100), Good(80), Satisfactory(60), NeedImprovement(40), Poor(20)
    }
}