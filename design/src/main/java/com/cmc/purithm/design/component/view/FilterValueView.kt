package com.cmc.purithm.design.component.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewFilterValueBinding

class FilterValueView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { initView(context) }

    private fun initView(context: Context): ViewFilterValueBinding {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return DataBindingUtil.inflate(inflater, R.layout.view_filter_value, this, true)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setFilterValue(name: String, value: Int) {
        val (filterName, filterImgRes) = getFilterRes(name)
        val imgDrawable = resources.getDrawable(filterImgRes, null)
        with(binding.tvFilterValueName) {
            text = "${filterName} $value"
            setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null)
        }
    }

    private fun getFilterRes(name: String) = when (name) {
        "lightBalance" -> "라이트밸런스" to R.drawable.ic_lightbalance
        "brightness" -> "밝기" to R.drawable.ic_lightness
        "exposure" -> "노출" to R.drawable.ic_exposure
        "contrast" -> "대비" to R.drawable.ic_contrast
        "highlight" -> "하이라이트" to R.drawable.ic_highlight
        "shadow" -> "그림자" to R.drawable.ic_shadow
        "saturation" -> "채도" to R.drawable.ic_saturation
        "tint" -> "틴트" to R.drawable.ic_tint
        "temperature" -> "색온도" to R.drawable.ic_warming
        "clear" -> "선명도" to R.drawable.ic_sharpness
        "clarity" -> "명료도" to R.drawable.ic_clarity
        else -> throw IllegalArgumentException("$name is not supported")
    }
}