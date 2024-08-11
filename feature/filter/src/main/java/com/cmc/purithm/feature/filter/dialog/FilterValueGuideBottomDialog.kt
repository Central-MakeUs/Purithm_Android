package com.cmc.purithm.feature.filter.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.DialogFilterGuideBinding

class FilterValueGuideBottomDialog : BaseBottomSheetDialogFragment<DialogFilterGuideBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_filter_guide

    override fun initView() {
        setTopMargin(40)

        binding.btnConfirm.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun initDataBinding() {

    }
}