package com.cmc.purithm.feature.review.ui

import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.review.Constants
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.FragmentReviewHistroyPictureBinding


class ReviewHistoryPictureFragment : BaseFragment<FragmentReviewHistroyPictureBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_review_histroy_picture

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