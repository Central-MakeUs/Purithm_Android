package com.cmc.purithm.design.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.cmc.purithm.design.R
import com.cmc.purithm.design.databinding.ViewSnackBarBinding
import com.google.android.material.snackbar.Snackbar

object Util {
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
    fun showPurithmSnackBar(view : View, message: String, actionText: String = "", action: (() -> Unit)? = null) {
        val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout
        snackBarLayout.setBackgroundColor(view.resources.getColor(R.color.transparent, null))

        val layoutInflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customSnackBar = ViewSnackBarBinding.inflate(layoutInflater)
        with(customSnackBar){
            tvText.text = message
            if(actionText.isNotEmpty()){
                tvAction.text = actionText
                tvAction.setOnClickListener {
                    action?.invoke()
                }
            }
        }
        snackBarLayout.addView(customSnackBar.root)
        snackBar.show()
    }
}