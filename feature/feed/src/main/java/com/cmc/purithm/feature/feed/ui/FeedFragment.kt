package com.cmc.purithm.feature.feed.ui

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
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
                                sideEffect.filterId, false
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

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            CommonDialogFragment.showDialog(
                content = "앱을 종료하시겠습니까?",
                negativeText = getString(com.cmc.purithm.design.R.string.content_cancel),
                negativeClickEvent = {
                    CommonDialogFragment.dismissDialog()
                },
                positiveText = "종료",
                positiveClickEvent = {
                    CommonDialogFragment.dismissDialog()
                    requireActivity().finish()
                },
                fragmentManager = childFragmentManager
            )
        }
    }

    override fun initView() {
        setBackButtonEvent()
        with(binding){
            listFeed.adapter = feedAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = "Feed"
            )
        }
    }
}