package com.cmc.purithm.feature.feed.dialog

import androidx.fragment.app.viewModels
import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.common.util.getColorResource
import com.cmc.purithm.feature.feed.viewModel.FeedViewModel
import com.cmc.purithm.feature.feed.R
import com.cmc.purithm.feature.feed.databinding.DialogFeedSortBinding

class FeedSortBottomDialog : BaseBottomSheetDialogFragment<DialogFeedSortBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_feed_sort

    private val viewModel: FeedViewModel by viewModels({
        requireParentFragment()
    })

    override fun initView() {
        with(getCurrentSortedByTextView(viewModel.state.value.sortedBy)) {
            setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
            setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
        }

        with(binding) {
            tvFilterEarliest.setOnClickListener {
                viewModel.updateFeedSortedBy("오래된순")
                dismissAllowingStateLoss()
            }
            tvArtistFilterLatest.setOnClickListener {
                viewModel.updateFeedSortedBy("최신순")
                dismissAllowingStateLoss()
            }
            tvFilterReviewPure.setOnClickListener {
                viewModel.updateFeedSortedBy("퓨어지수 높은순")
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {}

    private fun getCurrentSortedByTextView(sortedBy: String) = when (sortedBy) {
        "최신순" -> binding.tvArtistFilterLatest
        "오래된순" -> binding.tvFilterEarliest
        "퓨어지수 높은순" -> binding.tvFilterReviewPure
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }
}