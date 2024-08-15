package com.cmc.purithm.design.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewFilterChipBinding

class FilterChip @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {
    private val binding by lazy { initView(context) }

    private fun initView(context: Context): ViewFilterChipBinding {
        val inflater = LayoutInflater.from(context)
        return DataBindingUtil.inflate(inflater, R.layout.view_filter_chip, this, true)
    }

    fun setInitInfo(imgUrl: String, filterName: String, clickEvent: () -> Unit) {
        with(binding) {
            this.imgUrl = imgUrl
            this.filterName = filterName

            root.setOnClickListener {
                clickEvent()
            }
        }
    }
}