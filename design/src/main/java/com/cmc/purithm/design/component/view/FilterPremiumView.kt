package com.cmc.purithm.design.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ListFilterPremiumBinding

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

    fun setViewType(type: FilterMembership) {
        with(binding) {
            root.visibility = View.VISIBLE
            when (type) {
                FilterMembership.PREMIUM -> {
                    imgPremium.setImageResource(R.drawable.ic_premium)
                    tvPremium.text = context.getString(R.string.content_premium_filter)
                    tvPremiumDescription.setBackgroundResource(R.drawable.bg_text_filter_premium)
                    tvPremiumDescription.text = context.getString(R.string.content_premium_filter_stamp)
                }
                FilterMembership.PREMIUM_PLUS -> {
                    imgPremium.setImageResource(R.drawable.ic_premium_plus)
                    tvPremium.text = context.getString(R.string.content_premium_plus_filter)
                    tvPremiumDescription.setBackgroundResource(R.drawable.bg_text_filter_premium_plus)
                    tvPremiumDescription.text = context.getString(R.string.content_premium_plus_filter_stamp)
                }
                FilterMembership.BASIC -> {
                    binding.root.visibility = View.GONE
                }
            }
        }
    }

    enum class FilterMembership (val value : String){
        BASIC("BASIC"), PREMIUM("PREMIUM"), PREMIUM_PLUS("PREMIUM_PLUS")
    }
}