package com.cmc.purithm.feature.filter.ui

import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentFilterBinding

class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter
    private val navArgs by navArgs<FilterFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }

    override fun initObserving() {

    }

    override fun initBinding() {

    }

    override fun initView() {

    }
}