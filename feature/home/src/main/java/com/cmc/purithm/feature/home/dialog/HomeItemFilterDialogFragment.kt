package com.cmc.purithm.feature.home.dialog

import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.common.util.getColorResource
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.DialogHomeItemFilterBinding

/**
 * 홈 화면 정렬 바텀 시트 다이얼로그
 *
 * @param currentSortIndex 정렬에 사용된 인덱스(string array의 인덱스 참조)
 * @param clickEvent 정렬에 사용된 View를 클릭했을 때 사용
 * */
class HomeItemFilterDialogFragment(
    private val currentSortIndex: Int,
    private val clickEvent: (Int) -> Unit
) : BaseBottomSheetDialogFragment<DialogHomeItemFilterBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_home_item_filter
    private val filterSortTypeArray by lazy { resources.getStringArray(R.array.category_filter_sort) }

    override fun initView() {
        with(binding) {
            with(tvFilterPopular) {
                text = filterSortTypeArray[0]
                if (currentSortIndex == 0) {
                    setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
                    setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
                }
                setOnClickListener {
                    clickEvent(0)
                    dismissAllowingStateLoss()
                }
            }

            with(tvFilterNew) {
                text = filterSortTypeArray[1]
                if (currentSortIndex == 1) {
                    setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
                    setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
                }
                setOnClickListener {
                    clickEvent(1)
                    dismissAllowingStateLoss()
                }
            }

            with(tvFilterReviewHigh) {
                text = filterSortTypeArray[2]
                if (currentSortIndex == 2) {
                    setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
                    setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
                }
                setOnClickListener {
                    clickEvent(2)
                    dismissAllowingStateLoss()
                }
            }
        }
    }

    override fun initDataBinding() {

    }

}