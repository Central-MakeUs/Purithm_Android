package com.cmc.purithm.feature.artist.dialog

import androidx.fragment.app.viewModels
import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.common.util.getColorResource
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.databinding.DialogArtistItemSortBinding
import com.cmc.purithm.feature.artist.viewmodel.ArtistFilterViewModel

class ArtistFilterSortBottomDialog : BaseBottomSheetDialogFragment<DialogArtistItemSortBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_artist_item_sort

    private val viewModel: ArtistFilterViewModel by viewModels({
        requireParentFragment()
    })

    override fun initView() {
        with(getCurrentSortedByTextView(viewModel.state.value.sortedBy)) {
            setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
            setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
        }

        with(binding) {
            tvFilterView.setOnClickListener {
                viewModel.updateArtistSortedBy("멤버십 낮은순")
                dismissAllowingStateLoss()
            }
            tvArtistFilterLatest.setOnClickListener {
                viewModel.updateArtistSortedBy("이름순")
                dismissAllowingStateLoss()
            }
            tvFilterReviewPure.setOnClickListener {
                viewModel.updateArtistSortedBy("퓨어지수 높은순")
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {

    }

    private fun getCurrentSortedByTextView(sortedBy: String) = when (sortedBy) {
        "이름순" -> binding.tvArtistFilterLatest
        "멤버십 낮은순" -> binding.tvFilterView
        "퓨어지수 높은순" -> binding.tvFilterReviewPure
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }
}