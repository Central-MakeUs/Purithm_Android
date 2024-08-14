package com.cmc.purithm.feature.review.dialog

import android.os.Handler
import android.os.Looper
import com.cmc.purithm.common.base.BaseDialogFragment
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.DialogReviewCompleteBinding

class ReviewSuccessDialog (
    private val afterDelayEvent : () -> Unit
): BaseDialogFragment<DialogReviewCompleteBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_review_complete
    override val dialogType: BaseDialogType
        get() = BaseDialogType.FULL_SIZE

    override fun initDataBinding() {
    }

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            afterDelayEvent()
            dismissAllowingStateLoss()
        }, DEALY_TIEM)
    }

    companion object {
        private const val DEALY_TIEM = 3000L
    }
}