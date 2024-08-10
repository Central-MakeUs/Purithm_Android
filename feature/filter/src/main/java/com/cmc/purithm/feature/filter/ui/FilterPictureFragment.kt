package com.cmc.purithm.feature.filter.ui

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.util.getSerializableData
import com.cmc.purithm.domain.entity.filter.FilterImg
import com.cmc.purithm.feature.filter.Constants
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentFilterPictureBinding
import com.cmc.purithm.feature.filter.model.FilterImgType
import com.cmc.purithm.feature.filter.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint

class FilterPictureFragment : BaseFragment<FragmentFilterPictureBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_picture
    private val viewModel : FilterViewModel by activityViewModels()
    private val filterImgUrl by lazy { arguments?.getSerializableData(Constants.BUNDLE_FILTER_IMG_KEY) as FilterImg? }

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        binding.filterImgUrl = filterImgUrl
        binding.vm = viewModel
    }
}