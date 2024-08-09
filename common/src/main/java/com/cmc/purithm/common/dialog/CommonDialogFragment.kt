package com.cmc.purithm.common.dialog

import android.view.View
import androidx.fragment.app.FragmentManager
import com.cmc.purithm.common.R
import com.cmc.purithm.common.base.BaseDialogFragment
import com.cmc.purithm.common.databinding.DialogCommonBinding

class CommonDialogFragment(
    private val content: String,
    private val negativeText: String? = null,
    private val positiveText: String? = null,
    private val negativeClickEvent: (() -> Unit)?,
    private val positiveClickEvent: (() -> Unit)?
) : BaseDialogFragment<DialogCommonBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_common

    override fun initDataBinding() {
        with(binding) {
            content = this@CommonDialogFragment.content
        }
    }

    override fun initView() {
        with(binding) {
            if (negativeClickEvent != null && negativeText != null) {
                tvNegativeButton.text = negativeText
                tvNegativeButton.visibility = View.VISIBLE
                tvNegativeButton.setOnClickListener {
                    negativeClickEvent.invoke()
                }
            }

            if (positiveClickEvent != null && positiveText != null) {
                tvPositiveButton.text = positiveText
                tvPositiveButton.visibility = View.VISIBLE
                tvPositiveButton.setOnClickListener {
                    positiveClickEvent.invoke()
                }
            }
        }
    }

    companion object {
        private var INSTANCE : CommonDialogFragment? = null
        fun showDialog(
            content: String,
            negativeText: String? = null,
            positiveText: String? = null,
            negativeClickEvent: (() -> Unit)? = null,
            positiveClickEvent: (() -> Unit)? = null,
            fragmentManager : FragmentManager
        ) {
            dismissDialog()
            INSTANCE = CommonDialogFragment(
                content, negativeText, positiveText, negativeClickEvent, positiveClickEvent
            ).apply {
                show(fragmentManager, CommonDialogFragment::class.java.simpleName)
            }
        }
        fun dismissDialog() {
            if(INSTANCE != null && INSTANCE?.isAdded == true) {
                INSTANCE?.dismissAllowingStateLoss()
            }
            INSTANCE = null
        }
    }
}