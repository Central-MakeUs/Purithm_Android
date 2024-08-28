package com.cmc.purithm.design.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewSnackBarBinding
import com.google.android.material.snackbar.Snackbar

object Util {
    private const val TAG = "Util"

    inline val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()

    inline val Float.dp: Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
        )

    fun Context.getColorResource(id: Int) = ResourcesCompat.getColor(resources, id, null)

    @SuppressLint("RestrictedApi")
    fun showPurithmSnackBar(
        view: View,
        message: String,
        actionText: String = "",
        action: (() -> Unit)? = null,
        topVisible: Boolean
    ) {
        val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setBackgroundColor(view.resources.getColor(R.color.transparent, null))

        val snackBarParams = snackBar.view.layoutParams as ViewGroup.MarginLayoutParams
        val (topMargin, bottomMargin) = if (topVisible) {
            64.dp to (getScreenHeight(context = view.context) - 200)
        } else {
            0.dp to 48.dp
        }

        snackBarParams.setMargins(20.dp, topMargin, 20.dp, bottomMargin)
        snackBar.view.layoutParams = snackBarParams

        val layoutInflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customSnackBar = ViewSnackBarBinding.inflate(layoutInflater)
        with(customSnackBar) {
            tvText.text = message
            if (actionText.isNotEmpty()) {
                tvAction.text = actionText
                tvAction.setOnClickListener {
                    action?.invoke()
                }
            }
        }
        snackBarLayout.addView(customSnackBar.root)
        snackBar.show()
    }

    private fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        val size = android.graphics.Point()
        display.getSize(size)
        Log.d(TAG, "getScreenHeight: height pixel = ${size.y}")
        return size.y
    }

}