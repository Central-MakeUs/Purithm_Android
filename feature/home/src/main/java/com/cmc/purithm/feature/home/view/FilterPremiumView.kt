package com.cmc.purithm.feature.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.ListFilterPremiumBinding

class FilterPremiumView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { initView(context) }

    private fun initView(context: Context): ListFilterPremiumBinding {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return ListFilterPremiumBinding.inflate(inflater, this, true)
    }

    fun setViewType(type: FilterPremiumType) {
        with(binding) {
            root.visibility = View.VISIBLE
            when (type) {
                FilterPremiumType.PREMIUM -> {
                    imgPremium.setImageResource(com.cmc.purithm.design.R.drawable.ic_premium)
                    tvPremium.text = context.getString(R.string.content_premium_filter)
                    tvPremiumDescription.text = context.getString(R.string.content_premium_filter_stamp)
                }
                FilterPremiumType.PREMIUM_PLUS -> {
                    imgPremium.setImageResource(com.cmc.purithm.design.R.drawable.ic_premium_plus)
                    tvPremium.text = context.getString(R.string.content_premium_plus_filter)
                    tvPremiumDescription.text = context.getString(R.string.content_premium_plus_filter_stamp)
                }
            }
        }
    }

    enum class FilterPremiumType {
        PREMIUM, PREMIUM_PLUS
    }
}