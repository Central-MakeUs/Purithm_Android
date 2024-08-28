package com.cmc.purithm.feature.feed.ui

import android.util.Log
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.coroutines.flow.collectLatest
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
                    viewModel.state.collectLatest { state ->
                        Log.d(TAG, "initObserving: list = ${state.data}")
                        if (state.loading) {
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }
                        if(state.error != null){
                            CommonDialogFragment.showDialog(
                                content = getString(com.cmc.purithm.design.R.string.error_common),
                                positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                positiveClickEvent = {
                                    requireActivity().finish()
                                },
                                fragmentManager = childFragmentManager
                            )
                        }
                        feedAdapter.submitList(state.data)
                        binding.listFeed.post {
                            binding.listFeed.scrollToPosition(0)
                        }
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
    companion object {
        private const val TAG = "FeedFragment"
    }
}