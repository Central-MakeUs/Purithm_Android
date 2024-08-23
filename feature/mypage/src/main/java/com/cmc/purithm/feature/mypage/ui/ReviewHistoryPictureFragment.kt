package com.cmc.purithm.feature.mypage.ui

import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.mypage.Constants
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentReviewHistoryPictureBinding

class ReviewHistoryPictureFragment : BaseFragment<FragmentReviewHistoryPictureBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_history_picture

    private val imgUrl by lazy {
        arguments?.getString(Constants.BUNDLE_REVIEW_HISTORY_IMG_KEY)
    }

    override fun initObserving() {
    }

    override fun initBinding() {
        binding.imgUrl = imgUrl
    }

    override fun initView() {
    }
}