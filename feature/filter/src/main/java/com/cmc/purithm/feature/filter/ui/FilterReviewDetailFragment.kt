package com.cmc.purithm.feature.filter.ui

import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.adapter.FilterReviewDetailAdapter
import com.cmc.purithm.feature.filter.databinding.FragmentFilterReviewDetailBinding

class FilterReviewDetailFragment : BaseFragment<FragmentFilterReviewDetailBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_review_detail

    private val navArgs by navArgs<FilterReviewDetailFragmentArgs>()
    private val reviewId by lazy { navArgs.reviewId }
    private val reviewList by lazy { navArgs.reviewList.toList() }
    private val filterReviewDetailAdapter by lazy { FilterReviewDetailAdapter(requireActivity()) }

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        Log.d(TAG, "initView: reviewId = $reviewId")
        Log.d(TAG, "initView: reviewList = $reviewList")

        with(binding) {
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "",
                backClickListener = { findNavController().popBackStack() }
            )
            listReviewDetail.adapter = filterReviewDetailAdapter
            filterReviewDetailAdapter.submitList(reviewList)
            listReviewDetail.scrollToPosition(
                filterReviewDetailAdapter.getReviewItemWithPosition(
                    reviewId
                )
            )
        }
    }
    companion object {
        private const val TAG = "FilterReviewDetailFragm"
    }
}