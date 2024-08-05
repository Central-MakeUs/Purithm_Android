package com.cmc.purithm.common.util

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat

inline fun <reified T> Bundle.getParcelableData(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable(key) as T?
    }
}

fun Context.getColorResource(resId : Int) = ResourcesCompat.getColor(resources, resId, null)
