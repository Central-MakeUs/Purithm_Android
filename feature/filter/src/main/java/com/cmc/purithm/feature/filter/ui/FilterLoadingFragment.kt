package com.cmc.purithm.feature.filter.ui

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentFilterLoadingBinding

class FilterLoadingFragment : BaseFragment<FragmentFilterLoadingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_loading

    private val navArgs  by navArgs<FilterFragmentArgs>()

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            navigate(FilterLoadingFragmentDirections.actionFilterLoadingFragmentToFilterValueFragment(navArgs.filterId))
        }, DELAY_TIME)
    }

    companion object {
        private const val TAG = "FilterLoadingFragment"
        private const val DELAY_TIME = 3000L
    }
}