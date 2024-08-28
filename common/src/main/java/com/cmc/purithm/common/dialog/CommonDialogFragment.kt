package com.cmc.purithm.common.dialog

import android.view.View
import androidx.fragment.app.FragmentManager
import com.cmc.purithm.common.R
import com.cmc.purithm.common.base.BaseDialogFragment
import com.cmc.purithm.common.databinding.DialogCommonBinding

class CommonDialogFragment(
    private val content: String,
    private val description: String? = null,
    private val negativeText: String? = null,
    private val positiveText: String? = null,
    private val negativeClickEvent: (() -> Unit)?,
    private val positiveClickEvent: (() -> Unit)?
) : BaseDialogFragment<DialogCommonBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_common

    override val dialogType: BaseDialogType
        get() = BaseDialogType.WRAP

    override fun initDataBinding() {
        with(binding) {
            content = this@CommonDialogFragment.content
        }
    }

    override fun initView() {
        isCancelable = false
        with(binding) {
            if (description == null) {
                tvDescription.visibility = View.GONE
            } else {
                tvDescription.visibility = View.VISIBLE
                tvDescription.text = description
            }
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
        private var INSTANCE: CommonDialogFragment? = null
        fun showDialog(
            content: String,
            description: String? = null,
            negativeText: String? = null,
            positiveText: String? = null,
            negativeClickEvent: (() -> Unit)? = null,
            positiveClickEvent: (() -> Unit)? = null,
            fragmentManager: FragmentManager
        ) {
            dismissDialog()
            INSTANCE = CommonDialogFragment(
                content,
                description,
                negativeText,
                positiveText,
                negativeClickEvent,
                positiveClickEvent
            ).apply {
                show(fragmentManager, CommonDialogFragment::class.java.simpleName)
            }
        }

        fun dismissDialog() {
            if (INSTANCE != null && INSTANCE?.isAdded == true) {
                INSTANCE?.dismissAllowingStateLoss()
            }
            INSTANCE = null
        }
    }
}