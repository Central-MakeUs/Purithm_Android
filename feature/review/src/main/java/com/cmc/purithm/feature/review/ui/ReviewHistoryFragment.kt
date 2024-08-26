package com.cmc.purithm.feature.review.ui

import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
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
    // 리뷰 작성에서 넘어올 시, 아에 다른곳으로 navigate해야함
    private val isWrite by lazy { navArgs.isWrite }
    private val navigateType by lazy { navArgs.navigateType }
    private lateinit var reviewPictureAdapter : ReviewPictureAdapter

    private fun backNavigate(){
        if(isWrite){
            (activity as NavigationAction).popBackStackAfterWriteReview(type = navigateType)
        } else {
            findNavController().popBackStack()
        }
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when(state) {
                            ReviewHistoryState.DeleteSuccess -> {
                                dismissLoadingDialog()
                                showSnackBar(binding.root, message = "삭제되었습니다.")
                                backNavigate()
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
                                    if(!::reviewPictureAdapter.isInitialized){
                                        reviewPictureAdapter = ReviewPictureAdapter(requireActivity(), state.data.pictures)
                                        vpPicture.adapter = reviewPictureAdapter
                                    }
                                    if(state.data.pictures.size > 1){
                                        indicatorPicture.setViewPager(vpPicture)
                                    } else {
                                        indicatorPicture.visibility = View.GONE
                                        imgListBackground.visibility = View.GONE
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
                                (activity as NavigationAction).navigateFilterItem(filterId, false)
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

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            backNavigate()
        }
    }

    override fun initView() {
        setBackButtonEvent()
        with(binding){
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "남긴 후기",
                backClickListener = {
                    backNavigate()
                }
            )

            btnDelete.setOnClickListener {
                CommonDialogFragment.showDialog(
                    content = "작성한 후기를 삭제할까요?",
                    positiveText = "삭제하기",
                    negativeText = "취소",
                    negativeClickEvent = {
                        CommonDialogFragment.dismissDialog()
                    },
                    positiveClickEvent = {
                        viewModel.deleteReview(reviewId)
                    },
                    fragmentManager = childFragmentManager
                )
            }

            viewFilterChip.setInitInfo(
                imgUrl = thumbnail,
                filterName = filterName,
                clickEvent = {
                    (activity as NavigationAction).navigateFilterItem(filterId = filterId, popUpTo = false)
                }
            )
        }
    }
}