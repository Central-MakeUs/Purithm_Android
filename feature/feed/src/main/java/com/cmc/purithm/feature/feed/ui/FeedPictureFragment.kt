package com.cmc.purithm.feature.feed.ui

import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.feed.Constants
import com.cmc.purithm.feature.feed.R
import com.cmc.purithm.feature.feed.databinding.FragmentFeedPictureBinding

class FeedPictureFragment : BaseFragment<FragmentFeedPictureBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_feed_picture

    private val imgUrl by lazy {
        arguments?.getString(Constants.BUNDLE_FEED_IMG_KEY)
    }

    override fun initObserving() {
    }

    override fun initBinding() {
        binding.imgUrl = imgUrl
    }

    override fun initView() {
    }
}