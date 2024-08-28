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
import com.cmc.purithm.feature.mypage.adapter.StampListAdapter
import com.cmc.purithm.feature.mypage.adapter.listener.HistoryClickListener
import com.cmc.purithm.feature.mypage.databinding.FragmentStampHistoryBinding
import com.cmc.purithm.feature.mypage.dialog.FilterLockGuideDialog
import com.cmc.purithm.feature.mypage.viewmodel.StampHistoryState
import com.cmc.purithm.feature.mypage.viewmodel.StampHistoryViewModel
import com.cmc.purithm.feature.mypage.viewmodel.StampSideEffects
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StampHistoryFragment : BaseFragment<FragmentStampHistoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_stamp_history
    private val viewModel by viewModels<StampHistoryViewModel>()
    private val stampAdapter by lazy {
        StampListAdapter(object : HistoryClickListener {
            override fun onReviewHistoryClick(
                reviewId: Long,
                filterId: Long,
                filterName: String,
                thumbnail: String
            ) {
                viewModel.clickReviewHistory(
                    reviewId = reviewId,
                    filterId = filterId,
                    filterName = filterName,
                    thumbnail = thumbnail
                )
            }

            override fun onStampThumbClick(filterId: Long, os : String) {
                viewModel.clickFilterThumbnail(filterId, os)
            }
        })
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is StampHistoryState.Error -> {
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

                            StampHistoryState.Loading -> showLoadingDialog()
                            is StampHistoryState.Success -> {
                                dismissLoadingDialog()
                                with(binding) {
                                    stampAdapter.submitList(state.data.historyList)
                                    data = state.data
                                    listStamp.adapter = stampAdapter
                                }
                            }

                            StampHistoryState.Initialize -> {
                                viewModel.getStamps()
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffects ->
                        when (sideEffects) {
                            StampSideEffects.NavigateFilterHistory -> navigate(StampHistoryFragmentDirections.actionStampHistoryFragmentToMyFilterHistoryFragment())
                            is StampSideEffects.NavigateReviewHistory -> (activity as NavigationAction).navigateMyReviewHistory(
                                sideEffects.reviewId,
                                sideEffects.filterId,
                                sideEffects.thumbnail,
                                sideEffects.filterName
                            )

                            StampSideEffects.ShowFilterLockGuide -> {
                                FilterLockGuideDialog().show(childFragmentManager, null)
                            }

                            is StampSideEffects.NavigateFilter -> {
                                (activity as NavigationAction).navigateFilterItem(sideEffects.filterId, false)
                            }

                            StampSideEffects.ShowOsNotMatchSnackBar -> showSnackBar(binding.root, message = getString(R.string.content_os_not_match), topVisible = true)
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
        with(binding) {
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_DEFAULT,
                title = "누적 스탬프",
                questionClickListener = {
                    viewModel.clickStampLockDialog()
                },
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }
}