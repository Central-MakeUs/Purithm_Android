package com.cmc.purithm.feature.filter.ui

import android.view.View
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

    private val gradientVisibility by lazy {
        arguments?.getBoolean(Constants.BUNDLE_FILTER_REVIEW_IMG_GRADIENT)
    }

    override fun initObserving() {
    }

    override fun initBinding() {
        binding.imgUrl = imgUrl
        if(gradientVisibility == true){
            binding.imgListBackground.visibility = View.VISIBLE
        }
    }

    override fun initView() {
    }
}