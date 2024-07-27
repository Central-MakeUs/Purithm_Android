package com.cmc.purithm.feature.term.ui

import android.os.Handler
import android.os.Looper
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.feature.term.R
import com.cmc.purithm.feature.term.databinding.FragmentJoinCompleteBinding

class JoinCompleteFragment : BaseFragment<FragmentJoinCompleteBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_join_complete

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        Handler(Looper.getMainLooper()).postDelayed({
            (activity as NavigationAction).navigateHome()
        }, DELAY_TIME)
    }

    companion object {
        private const val DELAY_TIME = 3000L
    }
}