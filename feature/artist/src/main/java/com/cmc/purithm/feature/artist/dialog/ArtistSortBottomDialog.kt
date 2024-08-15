package com.cmc.purithm.feature.artist.dialog

import androidx.fragment.app.viewModels
import com.cmc.purithm.common.base.BaseBottomSheetDialogFragment
import com.cmc.purithm.common.util.getColorResource
import com.cmc.purithm.feature.artist.R
import com.cmc.purithm.feature.artist.databinding.DialogArtistSortBinding
import com.cmc.purithm.feature.artist.viewmodel.ArtistViewModel

class ArtistSortBottomDialog : BaseBottomSheetDialogFragment<DialogArtistSortBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.dialog_artist_sort

    private val viewModel: ArtistViewModel by viewModels({
        requireParentFragment()
    })

    override fun initView() {
        with(getCurrentSortedByTextView(viewModel.state.value.sortedBy)) {
            setTextAppearance(com.cmc.purithm.design.R.style.kr_body_1)
            setTextColor(requireContext().getColorResource(com.cmc.purithm.design.R.color.grey_500))
        }

        with(binding){
            tvSortFilter.setOnClickListener {
                viewModel.updateArtistSortedBy("필터 많은순")
                dismissAllowingStateLoss()
            }
            tvFilterLatest.setOnClickListener {
                viewModel.updateArtistSortedBy("최신순")
                dismissAllowingStateLoss()
            }
            tvFilterEarliest.setOnClickListener {
                viewModel.updateArtistSortedBy("오래된순")
                dismissAllowingStateLoss()
            }
        }
    }

    override fun initDataBinding() {
        binding.vm = viewModel
    }

    private fun getCurrentSortedByTextView(sortedBy : String) = when(sortedBy){
        "필터 많은순" -> binding.tvSortFilter
        "최신순" -> binding.tvFilterLatest
        "오래된순" -> binding.tvFilterEarliest
        else -> throw IllegalArgumentException("Invalid sortedBy")
    }
}