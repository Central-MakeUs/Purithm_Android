package com.cmc.purithm.feature.home.dialog

import androidx.fragment.app.viewModels
import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.common.util.getColorResource
import com.cmc.purithm.feature.home.HomeViewModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.DialogHomeItemFilterBinding

/**
 * 홈 화면 정렬 바텀 시트 다이얼로그
 * */
class HomeItemFilterDialogFragment(
) : BaseBottomSheetDialogFragment<DialogHomeItemFilterBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_home_item_filter

    // 부모 fragment의 viewModel을 사용
    private val viewModel : HomeViewModel by viewModels({
        requireParentFragment()
    })
    override fun initView() {
        with(getCurrentSortedByTextView(viewModel.state.value.sortedBy)) {
            setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
            setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
        }

        with(binding){
            tvFilterPopular.setOnClickListener {
                viewModel.updateFilterSortedBy("인기순")
                dismissAllowingStateLoss()
            }
            tvFilterLatest.setOnClickListener {
                viewModel.updateFilterSortedBy("최신순")
                dismissAllowingStateLoss()
            }
            tvFilterReviewHigh.setOnClickListener {
                viewModel.updateFilterSortedBy("퓨어지수 높은순")
                dismissAllowingStateLoss()
            }
        }
    }

    private fun getCurrentSortedByTextView(sortedBy : String) = when(sortedBy){
        "인기순" -> binding.tvFilterPopular
        "최신순" -> binding.tvFilterLatest
        "퓨어지수 높은순" -> binding.tvFilterReviewHigh
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }

    override fun initDataBinding() {
        binding.vm = viewModel
    }

}