package com.cmc.purithm.design.component.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
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
        Log.d("TAG", "setFilterValue: value = $value")
        val (filterName, filterImgRes) = getFilterRes(name)
        val imgDrawable = resources.getDrawable(filterImgRes, null)
        with(binding.tvFilterValueName) {
            text = "${filterName} $value"
            setCompoundDrawablesWithIntrinsicBounds(imgDrawable, null, null, null)
        }
    }

    private fun getFilterRes(name: String) = when (name) {
        context.getString(R.string.category_filter_value_lightBalance) ->  context.getString(R.string.content_filter_value_lightBalance) to R.drawable.ic_lightbalance
        context.getString(R.string.category_filter_value_brightness) ->  context.getString(R.string.content_filter_value_brightness) to R.drawable.ic_lightness
        context.getString(R.string.category_filter_value_exposure) ->  context.getString(R.string.content_filter_value_exposure) to R.drawable.ic_exposure
        context.getString(R.string.category_filter_value_contrast) ->  context.getString(R.string.content_filter_value_contrast) to R.drawable.ic_contrast
        context.getString(R.string.category_filter_value_highlight) ->  context.getString(R.string.content_filter_value_highlight) to R.drawable.ic_highlight
        context.getString(R.string.category_filter_value_shadow) ->  context.getString(R.string.content_filter_value_shadow) to R.drawable.ic_shadow
        context.getString(R.string.category_filter_value_saturation) ->  context.getString(R.string.content_filter_value_saturation) to R.drawable.ic_saturation
        context.getString(R.string.category_filter_value_tint) ->  context.getString(R.string.content_filter_value_tint) to R.drawable.ic_tint
        context.getString(R.string.category_filter_value_temperature) ->  context.getString(R.string.content_filter_value_temperature) to R.drawable.ic_warming
        context.getString(R.string.category_filter_value_clear) ->  context.getString(R.string.content_filter_value_clear) to R.drawable.ic_sharpness
        context.getString(R.string.category_filter_value_clarity) -> context.getString(R.string.content_filter_value_clarity) to R.drawable.ic_clarity
        else -> throw IllegalArgumentException("$name is not supported")
    }
}