package com.cmc.purithm.feature.home.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.feature.home.R
import com.cmc.purithm.feature.home.databinding.DialogHomeItemPremiumBinding

class HomeFilterLockBottomDialog : BaseBottomSheetDialogFragment<DialogHomeItemPremiumBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_home_item_premium

    override fun initDataBinding() {}

    override fun initView() {
        with(binding){
            btnConfirm.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }
}