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
import com.cmc.purithm.feature.mypage.adapter.ReviewHistoryAdapter
import com.cmc.purithm.feature.mypage.databinding.FragmentReviewHistoryListBinding
import com.cmc.purithm.feature.mypage.viewmodel.ReviewHistorySideEffects
import com.cmc.purithm.feature.mypage.viewmodel.ReviewHistoryState
import com.cmc.purithm.feature.mypage.viewmodel.ReviewHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewHistoryListFragment : BaseFragment<FragmentReviewHistoryListBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_history_list
    private val viewModel by viewModels<ReviewHistoryViewModel>()
    private val reviewHistoryAdapter by lazy {
        ReviewHistoryAdapter(
            fragmentActivity = requireActivity(),
            viewModel = viewModel
        )
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is ReviewHistoryState.Error -> {
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

                            ReviewHistoryState.Initialize -> viewModel.getReviewHistory()
                            ReviewHistoryState.Loading -> showLoadingDialog()
                            is ReviewHistoryState.Success -> {
                                dismissLoadingDialog()
                                with(binding) {
                                    count = state.data.size
                                    reviewHistoryAdapter.submitList(state.data)
                                }
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            is ReviewHistorySideEffects.NavigateFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.filterId, false
                            )

                            ReviewHistorySideEffects.SuccessDeleteReview -> {
                                showSnackBar(binding.root, message = getString(R.string.content_delete_review_success))
                                viewModel.init()
                            }

                            is ReviewHistorySideEffects.ShowDeleteReviewDialog -> CommonDialogFragment.showDialog(
                                content = "작성한 후기를 삭제할까요?",
                                positiveText = "삭제하기",
                                negativeText = "취소",
                                positiveClickEvent = {
                                    viewModel.deleteReview(sideEffect.reviewId)
                                    CommonDialogFragment.dismissDialog()
                                },
                                negativeClickEvent = {
                                    CommonDialogFragment.dismissDialog()
                                },
                                fragmentManager = childFragmentManager
                            )

                            ReviewHistorySideEffects.ShowOsNotMatchSnackBar -> showSnackBar(binding.root, message = getString(R.string.content_os_not_match))
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
                title = "남긴 후기",
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
            listReviewHistory.adapter = reviewHistoryAdapter
            btnReviewWrite.setOnClickListener {
                navigate(ReviewHistoryListFragmentDirections.actionMyReviewHistoryFragmentToMyFilterHistoryFragment())
            }
        }
    }
}