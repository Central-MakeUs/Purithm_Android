package com.cmc.purithm.feature.mypage.dialog

import com.cmc.purithm.common.base.BaseDialogFragment
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.DialogFilterLockGuideBinding

class FilterLockGuideDialog : BaseDialogFragment<DialogFilterLockGuideBinding>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_filter_lock_guide
    override val dialogType: BaseDialogType
        get() = BaseDialogType.WRAP

    override fun initDataBinding() {

    }

    override fun initView() {
        binding.btnConfirm.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}