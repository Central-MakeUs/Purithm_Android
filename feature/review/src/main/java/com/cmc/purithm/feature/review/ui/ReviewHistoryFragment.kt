package com.cmc.purithm.feature.review.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.adapter.ReviewPictureAdapter
import com.cmc.purithm.feature.review.databinding.FragmentReviewHistoryBinding
import com.cmc.purithm.feature.review.viewmodel.ReviewHistorySideEffect
import com.cmc.purithm.feature.review.viewmodel.ReviewHistoryState
import com.cmc.purithm.feature.review.viewmodel.ReviewHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ReviewHistoryFragment : BaseFragment<FragmentReviewHistoryBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_review_history

    private val viewModel: ReviewHistoryViewModel by viewModels()
    private val navArgs by navArgs<ReviewHistoryFragmentArgs>()

    private val filterName by lazy { navArgs.filterName }
    private val filterId by lazy { navArgs.filterId }
    private val reviewId by lazy { navArgs.reviewId }
    private val thumbnail by lazy { navArgs.thumbnail }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when(state) {
                            ReviewHistoryState.DeleteSuccess -> {
                                dismissLoadingDialog()
                                CommonDialogFragment.showDialog(
                                    content = "삭제되었습니다.",
                                    positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                    positiveClickEvent = {
                                        // TODO : Navigate Check
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }
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
                            ReviewHistoryState.Initialize -> {
                                viewModel.getReviewItem(reviewId)
                            }
                            ReviewHistoryState.Loading -> showLoadingDialog()
                            is ReviewHistoryState.Success -> {
                                dismissLoadingDialog()
                                with(binding){
                                    data = state.data
                                    vpPicture.adapter = ReviewPictureAdapter(requireActivity(), state.data.pictures)
                                    if(state.data.pictures.size > 1){
                                        indicatorPicture.setViewPager(vpPicture)
                                    }
                                    viewReviewIntensity.setReviewIntensity(state.data.pureDegree)
                                }
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when(sideEffect){
                            ReviewHistorySideEffect.NavigateFilter -> {

                            }
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        with(binding){
            vm = viewModel
        }
    }

    override fun initView() {
        with(binding){
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "남긴 후기",
                backClickListener = {
                    // TODO : Navigate Check
                }
            )

            btnDelete.setOnClickListener {
                viewModel.deleteReview(reviewId)
            }

            viewFilterChip.setInitInfo(
                imgUrl = thumbnail,
                filterName = filterName,
                clickEvent = {
                    // TODO : Navigate Check
                }
            )
        }
    }
}