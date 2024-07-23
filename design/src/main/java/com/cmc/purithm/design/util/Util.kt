package com.cmc.purithm.design.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.core.content.res.ResourcesCompat

object Util {
    inline val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()

    inline val Float.dp: Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
        )

    fun Context.getColorResource(id : Int) = ResourcesCompat.getColor(resources, id, null)
}