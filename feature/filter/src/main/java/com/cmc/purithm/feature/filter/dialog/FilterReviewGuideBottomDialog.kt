package com.cmc.purithm.feature.filter.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.DialogReviewIntensityBinding

class FilterReviewGuideBottomDialog :
    BaseBottomSheetDialogFragment<DialogReviewIntensityBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_review_intensity

    override fun initView() {
        with(binding){
            viewRatingOutstanding.setReviewIntensity(100)
            viewRatingGood.setReviewIntensity(80)
            viewRatingSatisfactory.setReviewIntensity(60)
            viewRatingNeedImprovement.setReviewIntensity(40)
            viewRatingPoor.setReviewIntensity(20)

            btnConfirm.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {

    }
}