package com.cmc.purithm.feature.filter.ui

import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.filter.Constants
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentReviewDetailPictureBinding

class FilterReviewDetailPictureFragment : BaseFragment<FragmentReviewDetailPictureBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_review_detail_picture

    private val imgUrl by lazy {
        arguments?.getString(Constants.BUNDLE_FILTER_REVIEW_IMG_KEY)
    }

    override fun initObserving() {
    }

    override fun initBinding() {
        binding.imgUrl = imgUrl
    }

    override fun initView() {
    }
}