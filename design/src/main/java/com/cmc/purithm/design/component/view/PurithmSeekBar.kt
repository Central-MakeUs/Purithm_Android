package com.cmc.purithm.design.component.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmc.purithm.design.databinding.ViewSeekbarBinding

class PurithmSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        initView(context)
    }

    private lateinit var binding : ViewSeekbarBinding

    private fun initView(context: Context){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewSeekbarBinding.inflate(inflater, this, true).apply {
            with(seekbarMain){
                max = 100
                progress = 0
                incrementProgressBy(20)
            }
        }
    }
}