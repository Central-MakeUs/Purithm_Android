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
            viewRatingOutstanding.setReviewIntensity(20)
            viewRatingGood.setReviewIntensity(40)
            viewRatingSatisfactory.setReviewIntensity(60)
            viewRatingNeedImprovement.setReviewIntensity(80)
            viewRatingPoor.setReviewIntensity(100)

            btnConfirm.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {

    }
}