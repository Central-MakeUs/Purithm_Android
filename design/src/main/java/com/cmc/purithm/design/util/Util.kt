package com.cmc.purithm.design.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
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
    fun View.showPurithmSnackBar(message: String, actionText: String, action: (() -> Unit)?) {
        val rootView = findViewById<View>(android.R.id.content)
        val snackBar = Snackbar.make(rootView, "", Snackbar.LENGTH_INDEFINITE)
        val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
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