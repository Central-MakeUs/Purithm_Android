package com.cmc.purithm.common.dialog

import android.os.Looper
import com.cmc.purithm.common.R
import com.cmc.purithm.common.base.BaseDialogFragment
import com.cmc.purithm.common.databinding.DialogLoadingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Handler

/**
 * 로딩 상태를 표시하는 Dialog
 *
 * @since 2024-07-14
 * @author Yu Seung Woo
 * */
class LoadingDialogFragment : BaseDialogFragment<DialogLoadingBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_loading
    override val dialogType: BaseDialogType
        get() = BaseDialogType.FULL_SIZE

    override fun initDataBinding() {}
    override fun initView() {
        // 최대 시간 3초
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Main){
                delay(MAX_TIME)
                dismissAllowingStateLoss()
            }
        }
    }

    companion object {
        private const val MAX_TIME = 3000L
    }
}