package com.cmc.purithm.feature.feed.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.feed.viewModel.FeedSideEffects
import com.cmc.purithm.feature.feed.viewModel.FeedViewModel
import com.cmc.purithm.feature.feed.R
import com.cmc.purithm.feature.feed.adapter.FeedAdapter
import com.cmc.purithm.feature.feed.databinding.FragmentFeedBinding
import com.cmc.purithm.feature.feed.dialog.FeedSortBottomDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_feed

    private val viewModel: FeedViewModel by viewModels()
    private val feedAdapter by lazy { FeedAdapter(viewModel, requireActivity()) }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        if (state.loading) {
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }
                        feedAdapter.submitList(state.data)
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            is FeedSideEffects.NavigateFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.filterId
                            )

                            FeedSideEffects.ShowFeedSortBottomDialog -> FeedSortBottomDialog().show(
                                childFragmentManager,
                                null
                            )
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {
        with(binding){
            listFeed.adapter = feedAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = "Feed"
            )
        }
    }
}