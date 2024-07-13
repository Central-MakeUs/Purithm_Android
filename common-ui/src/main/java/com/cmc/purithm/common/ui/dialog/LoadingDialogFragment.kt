package com.cmc.purithm.common.ui.dialog

import com.cmc.purithm.common.ui.R
import com.cmc.purithm.common.ui.base.BaseDialogFragment
import com.cmc.purithm.common.ui.databinding.DialogLoadingBinding

/**
 * 로딩 상태를 표시하는 Dialog
 *
 * @since 2024-07-14
 * @author Yu Seung Woo
 * */
class LoadingDialogFragment : BaseDialogFragment<DialogLoadingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_loading

    override fun initDataBinding() {}
    override fun initView() {}
}