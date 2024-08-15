package com.cmc.purithm.feature.artist.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.databinding.DialogArtistFilterLockBinding
import com.cmc.purithm.feature.artist.viewmodel.ArtistFilterViewModel

class ArtistFilterLockBottomDialog :
    BaseBottomSheetDialogFragment<DialogArtistFilterLockBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_artist_filter_lock

    override fun initView() {
        binding.btnConfirm.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun initDataBinding() {
    }
}