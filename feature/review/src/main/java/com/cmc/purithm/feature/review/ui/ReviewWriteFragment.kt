package com.cmc.purithm.feature.review.ui

import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.FragmentReviewWriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewWriteFragment : BaseFragment<FragmentReviewWriteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_write
    private val navArgs by navArgs<ReviewWriteFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }

    override fun initObserving() {

    }

    override fun initBinding() {

    }

    override fun initView() {

    }
}