package com.cmc.purithm.feature.filter.ui

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
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
    private val viewModel : FilterViewModel by hiltNavGraphViewModels(R.id.nav_filter)
    private val filterImg : String by lazy {
        arguments?.getString(Constants.BUNDLE_FILTER_IMG_KEY, "") ?: ""
    }
    private val originalImg : String by lazy {
        arguments?.getString(Constants.BUNDLE_FILTER_ORIGINAL_KEY, "") ?: ""
    }

    override fun initObserving() {}

    override fun initBinding() {
        with(binding){
            vm = viewModel
            filterImg = this@FilterPictureFragment.filterImg
            originalImg = this@FilterPictureFragment.originalImg
        }
    }

    override fun initView() {}

    companion object{
        private const val TAG = "FilterPictureFragment"
    }
}