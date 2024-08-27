package com.cmc.purithm.feature.filter.ui

import android.os.Handler
import android.os.Looper
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentFilterLoadingBinding

class FilterLoadingFragment : BaseFragment<FragmentFilterLoadingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_loading
    private val navArgs by navArgs<FilterLoadingFragmentArgs>()
    private val filterName by lazy { navArgs.filterName }

    override fun initObserving() {}

    override fun initBinding() {
        binding.filterName = filterName
    }

    override fun initView() {
        setBackButtonEvent()
        Handler(Looper.getMainLooper()).postDelayed({
            navigate(FilterLoadingFragmentDirections.actionFilterLoadingFragmentToFilterValueFragment(navArgs.filterId))
        }, DELAY_TIME)
    }

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
    }

    companion object {
        private const val TAG = "FilterLoadingFragment"
        private const val DELAY_TIME = 2000L
    }
}