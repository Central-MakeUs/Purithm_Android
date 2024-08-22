package com.cmc.purithm.feature.mypage.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.DialogProfileImgBinding

class SetProfileImgDialog(
    private val clickEvent : (String) -> Unit
) : BaseBottomSheetDialogFragment<DialogProfileImgBinding>(){
    override val layoutResourceId: Int
        get() = R.layout.dialog_profile_img

    override fun initView() {
        with(binding){
            tvDefaultImg.setOnClickListener {
                clickEvent("clear")
                dismissAllowingStateLoss()
            }

            tvStartGallery.setOnClickListener {
                clickEvent("gallery")
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {

    }
}