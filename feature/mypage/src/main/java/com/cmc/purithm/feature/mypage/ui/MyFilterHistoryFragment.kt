package com.cmc.purithm.feature.mypage.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.adapter.FilterHistoryAdapter
import com.cmc.purithm.feature.mypage.adapter.listener.FilterHistoryClickListener
import com.cmc.purithm.feature.mypage.adapter.listener.HistoryClickListener
import com.cmc.purithm.feature.mypage.databinding.FragmentFilterHistoryBinding
import com.cmc.purithm.feature.mypage.viewmodel.FilterHistorySideEffects
import com.cmc.purithm.feature.mypage.viewmodel.FilterHistoryState
import com.cmc.purithm.feature.mypage.viewmodel.FilterHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFilterHistoryFragment : BaseFragment<FragmentFilterHistoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_history

    private val viewModel by viewModels<FilterHistoryViewModel>()
    private val filterHistoryAdapter by lazy {
        FilterHistoryAdapter(historyClickListener = object : HistoryClickListener {
            override fun onReviewHistoryClick(
                reviewId: Long,
                filterId: Long,
                filterName: String,
                thumbnail: String
            ) {
                viewModel.clickReviewHistory(filterId, reviewId, thumbnail, filterName)
            }

            override fun onStampThumbClick(filterId: Long, os : String) {
                viewModel.clickFilterThumbnail(filterId, os)
            }

        }, filterHistoryClickListener = object : FilterHistoryClickListener {
            override fun onReviewWriteClick(filterId: Long, thumbnail: String, filterName: String) {
                viewModel.clickFilterWriteReview(filterId, thumbnail, filterName)
            }

            override fun onFilterValueClick(filterId: Long, os : String) {
                viewModel.clickFilterValue(filterId, os)
            }
        })
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is FilterHistoryState.Error -> {
                                dismissLoadingDialog()
                                CommonDialogFragment.showDialog(
                                    content = getString(com.cmc.purithm.design.R.string.error_common),
                                    positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                    positiveClickEvent = {
                                        requireActivity().finish()
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }

                            FilterHistoryState.Initialize -> {
                                viewModel.getFilterHistory()
                            }

                            FilterHistoryState.Loading -> showLoadingDialog()
                            is FilterHistoryState.Success -> {
                                dismissLoadingDialog()
                                with(binding) {
                                    count = state.data.count
                                    filterHistoryAdapter.submitList(state.data.historyList)
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            is FilterHistorySideEffects.NavigateFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.filterId,
                                false
                            )

                            is FilterHistorySideEffects.NavigateFilterValue -> (activity as NavigationAction).navigateFilterValue(
                                sideEffect.filterId
                            )

                            is FilterHistorySideEffects.NavigateReviewHistory -> (activity as NavigationAction).navigateMyReviewHistory(
                                sideEffect.reviewId,
                                sideEffect.filterId,
                                sideEffect.thumbnail,
                                sideEffect.filterName
                            )

                            is FilterHistorySideEffects.NavigateReviewWrite -> (activity as NavigationAction).navigateReviewWrite(
                                navigateType = 2,
                                sideEffect.filterName,
                                sideEffect.filterId,
                                sideEffect.thumbnail,
                            )

                            FilterHistorySideEffects.ShowOsNotMatchSnackBar -> showSnackBar(binding.root, getString(R.string.content_os_not_match))
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {

    }

    override fun initView() {
        with(binding) {
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "필터 열람 내역",
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
            listFilterHistory.adapter = filterHistoryAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }
}